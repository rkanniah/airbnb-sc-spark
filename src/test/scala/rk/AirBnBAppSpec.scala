package rk

import org.apache.spark.sql.SparkSession
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import com.typesafe.config._

/**
 * Note: running this test from an IDE such as Eclipse will fail because of the file
 * path confusion between application.test.conf & application.conf. But running from
 * sbt console would avoid this problem.
 */
class AirBnBAppSpec extends AnyFunSuite with Matchers with BeforeAndAfterEach {

  var sparkTestSession: SparkSession = _
  var testFilePath: String = _

  override def beforeEach() {
    sparkTestSession = SparkSession.builder.master("local[1]").appName("AirBnBApp Testing").getOrCreate()

    //Retrieve test file path from test config
    val conf = ConfigFactory.load()
    testFilePath = conf.getString("testFilePath")
    println(s"########## testFilePath: $testFilePath ##########")
  }

  test("An empty Set should have size 0") {

    val csvFile = sparkTestSession.read.options(Map("delimiter" -> ",", "header" -> "true")).csv(testFilePath).cache()
    val dbDF = AirBnBApp.transformCsvToDbDataFile(csvFile)

    //For the purpose of verifying the number of records found
    //in tabular format
    dbDF.show()

    //Assert exactly one valid record is found out of 2 records
    assert(dbDF.count() == 1)
  }

  override def afterEach() {
    sparkTestSession.stop()
  }
}
