package rk

import java.util.Properties

import org.apache.spark.sql.{ Dataset, Row, SparkSession }
import org.apache.spark.sql.functions.regexp_replace
import org.apache.spark.sql.functions.when
import org.apache.spark.sql.types.{ DoubleType, IntegerType }

import com.typesafe.config.ConfigFactory

/**
 * A simple program to process an AirBnB csv file.
 */
object AirBnBApp extends ProcessingRule with App {

  //Retrieve file path from config
  val conf = ConfigFactory.load()
  val filePath = conf.getString("filePath")
  println(s"########## filePath: $filePath ##########")

  //Get a Spark session and load the csv file
  val mySparkSession = SparkSession.builder.master("local[1]").appName("AirBnBApp").getOrCreate()
  val csvFile = mySparkSession.read.options(Map("delimiter" -> ",", "header" -> "true")).csv(filePath).cache()

  //Get a database ready format
  val dbDF = transformCsvToDbDataFile(csvFile)

  //Get database connection details
  val dbUser = conf.getString("database.user")
  val dbPassword = conf.getString("database.password")
  val dbUrl = conf.getString("database.url")

  //Set database connection details
  val connectionProperties = new Properties()
  connectionProperties.put("user", dbUser)
  connectionProperties.put("password", dbPassword)

  //Write to data to postgresql database
  val writeMode = conf.getString("write.mode")
  dbDF.write
    .format("jdbc")
    .mode(writeMode)
    .jdbc(dbUrl, "listing", connectionProperties)

  mySparkSession.stop()
}

trait ProcessingRule {

  /**
   * Prepare a suitable format to be saved into Postgres database.
   * General rules are
   * 1. Look for minimum nights > 1 & maximum nights <= 30
   * 2. Look amenities such as Wifi, TV, and Internet
   * 3. Replace the $ signs in price & weekly_price with empty string
   * 4. Convert minimum nights and maximum nights to Integer
   * 5. Convert price & weekly_price to Double
   * 6. When weekly_price is null then set it as 0
   */
  def transformCsvToDbDataFile(csvFile: Dataset[Row]): Dataset[Row] = {

    csvFile
      .select("id", "listing_url", "amenities", "minimum_nights", "maximum_nights", "price", "weekly_price", "city", "country")
      .filter(csvFile.col("amenities").contains("Internet") &&
        csvFile.col("amenities").contains("Wifi") &&
        csvFile.col("amenities").contains("TV"))
      .filter(csvFile.col("price").contains("$"))
      .where("minimum_nights > 1 and maximum_nights <= 30")
      .withColumn("id", csvFile.col("id").cast(IntegerType))
      .withColumn("minimum_nights", csvFile.col("minimum_nights").cast(IntegerType))
      .withColumn("maximum_nights", csvFile.col("maximum_nights").cast(IntegerType))
      .withColumn("price", regexp_replace(csvFile.col("price"), "\\$", "").cast(DoubleType))
      .withColumn("weekly_price", when(csvFile.col("weekly_price").isNull, "0")
        .otherwise(regexp_replace(csvFile.col("weekly_price"), "\\$", ""))
        .cast(DoubleType))
      .limit(100)
  }
}
