import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Shilpika on 6/27/16.
  */
object MemTimeCheck {



  def main(args: Array[String]): Unit={


    val conf = new SparkConf().setAppName("Create-Parquet")

    val sc = new SparkContext(conf)

    // sc is an existing SparkContext.
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._
    // Create an RDD
    val number = sc.textFile("/projects/ExaHDF5/sshilpika/bf2.txt")

    // The schema is encoded in a string
    val schemaString = "num"

    // Import Row.
    import org.apache.spark.sql.Row

    // Import Spark SQL data types
    import org.apache.spark.sql.types.{StructType,StructField,StringType};

    // Generate the schema based on the string of schema
    val schema =
      StructType(
        schemaString.split(" ").map(fieldName => StructField(fieldName, StringType, true)))

    // Convert records of the RDD (people) to Rows.
    val rowRDD = number.map(p => Row(p.trim))

    // Apply the schema to the RDD.
    val numberDataFrame = sqlContext.createDataFrame(rowRDD, schema)

    // Register the DataFrames as a table.
    numberDataFrame.registerTempTable("numbers")

    // SQL statements can be run by using the sql methods provided by sqlContext.
    val results = sqlContext.sql("SELECT num FROM numbers")

    // The results of SQL queries are DataFrames and support all the normal RDD operations.
    // The columns of a row in the result can be accessed by field index or by field name.
    results.map(t => "Number: " + t(0)).collect().foreach(println)


    results.write.parquet("/projects/ExaHDF5/sshilpika/bf.parquet")
  }



}
