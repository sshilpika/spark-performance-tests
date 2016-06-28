import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Shilpika on 6/27/16.
  */
object MemTimeCheck {

  case class Config(dir: Option[String] = None, ext: Option[String] = None, slices: Int = 4, executor: Int = 1, fileN: String = "")


  def parseCommandLine(args: Array[String]): Option[Config] = {
    val parser = new scopt.OptionParser[Config]("scopt") {
      head("LineCount", "1.0")
      opt[String]('d', "dir") action { (x, c) =>
        c.copy(dir = Some(x))
      } text ("dir is a String property")
      opt[String]('e', "extension") action { (x, c) =>
        c.copy(ext = Some(x))
      } text ("ext is a String property")
      opt[Int]('s', "slices") action { (x, c) =>
        c.copy(slices = x)
      } text ("slices is an Int property")
      opt[Int]('x', "executor") action { (x, c) =>
        c.copy(executor = x)
      } text ("executor is an Int property")
      opt[String]('f', "fileN") action { (x, c) =>
        c.copy(fileN = x)
      } text ("FileName is a String property")
      help("help") text ("prints this usage text")

    }
    // parser.parse returns Option[C]
    parser.parse(args, Config())
  }

  def main(args: Array[String]): Unit={
    val conf = new SparkConf().setAppName("LineCount File I/O").set("spark.cores.max","3").set("spark.executor.cores","1")//.set("spark.metrics.conf","")
    val spark = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(spark)
    import sqlContext.implicits._

    val appConfig = parseCommandLine(args).getOrElse(Config())
//    val path = appConfig.dir.getOrElse("./data")
//    val extension = appConfig.ext.getOrElse(".txt")
//    val slices = appConfig.slices
//    val exec = appConfig.executor
//    val fileN = appConfig.fileN

    //val rdd = spark.parallelize(1 to 100000000, 3)
    val rdd = spark.textFile("/projects/ExaHDF5/sshilpika/bF.txt")

    val result = rdd.map(_ + 5).count()

    println(s"The result is $result the default cores are ${spark.defaultParallelism} and partitions used  are ${rdd.partitions.length}")
    println(s"The debug string is ${rdd.toDebugString}")

    //spark.stop()
  }



}
