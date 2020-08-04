# airbnb-sc-spark

This is a project to read & process a csv file and then write into a PostgreSql database using Spark written in Scala 2.12.x. The listing.csv file for the city of Paris, found in [Inside Airbnb](http://insideairbnb.com/get-the-data.html) website.

This publicly available file is provided by them under [Creative Commons CC0 1.0 Universal (CC0 1.0) "Public Domain Dedication"](https://creativecommons.org/publicdomain/zero/1.0/) license.

NOTE: The listings.csv file is huge therefore appropriate to set the limit on the number of rows to process in Spark.

Reference:
-------------
- [SO community guidance on Spark testing](https://stackoverflow.com/questions/43729262/how-to-write-unit-tests-in-spark-2-0)
- [SO community guidance on testing file configuration](https://stackoverflow.com/questions/30670437/how-to-specify-different-application-conf-for-specs2-tests)
