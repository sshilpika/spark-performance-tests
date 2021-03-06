import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Shilpika on 6/27/16.
  */
object MemTimeCheck {

  case class Config(dir: Option[String] = None, ext: Option[String] = None,
                    slices: Int = 1, executor: String = "1g", fileN: String = "",
    maxc:Int= 1, ecore:Int = 1)


  def parseCommandLine(args: Array[String]): Option[Config] = {
    val parser = new scopt.OptionParser[Config]("scopt") {
      head("LineCount", "1.0")
      opt[String]('d', "dir") action { (x, c) =>
        c.copy(dir = Some(x))
      } text ("dir is a String property")
      opt[Int]('c', "ecore") action { (x, c) =>
        c.copy(ecore = x)
      } text ("dir is a String property")
      opt[Int]('m', "maxc") action { (x, c) =>
        c.copy(maxc = x)
      } text ("max core is a Int property")
      opt[String]('e', "extension") action { (x, c) =>
        c.copy(ext = Some(x))
      } text ("ext is a String property")
      opt[Int]('s', "slices") action { (x, c) =>
        c.copy(slices = x)
      } text ("slices is an Int property")
      opt[String]('x', "executor") action { (x, c) =>
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

    val appConfig = parseCommandLine(args).getOrElse(Config())
    val partitions = appConfig.slices
    val exec = appConfig.executor
    val maxc = appConfig.maxc
    val ecore = appConfig.ecore

    val conf = new SparkConf().setAppName("Spark-Performance-Cooley").
      set("spark.cores.max",maxc.toString).
      set("spark.executor.memory",exec).
      set("spark.executor.cores",ecore.toString)//.
      //set("","")
    val spark = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(spark)
    import sqlContext.implicits._


//    val path = appConfig.dir.getOrElse("./data")
//    val extension = appConfig.ext.getOrElse(".txt")

//    val fileN = appConfig.fileN

    //val rdd = spark.parallelize(1 to 100000000, 3)
    val rdd = spark.textFile("/projects/ExaHDF5/sshilpika/bF.txt",partitions)
    //val res1 = rdd.reduce(_+_)
    val result = rdd.map(_ + 5).count()

    println(s"The result is $result the default cores are ${spark.defaultParallelism} and partitions used  are ${rdd.partitions.length}")
    println(s"The debug string is ${rdd.toDebugString}")
    //println(s"The sum is ${res1}")
    //spark.stop()
  }



}
