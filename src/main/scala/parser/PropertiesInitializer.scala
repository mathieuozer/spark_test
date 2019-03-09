package parser

import java.nio.charset.CodingErrorAction
import java.util.Properties

import org.apache.spark.SparkConf

import scala.io.{Codec, Source}

object PropertiesInitializer {
  val properties = new Properties

  implicit val codec = Codec("UTF-8")
  codec.onMalformedInput(CodingErrorAction.REPLACE)
  codec.onUnmappableCharacter(CodingErrorAction.REPLACE)


  def readFile(filePath:String): Map[String,String]= {
    val lines = Source.fromFile(filePath).getLines()
      .filter(line => !line.contains("#") && !line.isEmpty)
      .filter(line => line.contains("="))
     val arr = lines.map(line => line.split("=")).filter(arr => arr.size > 1).map(x => (x(0),x(1)))
     arr.toMap
  }

  def loadProperties(props : Map[String,String]): Properties ={
    for((k,v) <- props){
      properties.put(k,v)
    }
    properties
  }

  def loadSparkConf(filePath:String) : Map[String,String] ={
    readFile(filePath).filter(p => p._1.startsWith("spark"))
  }

  def getProperties(filePath:String): Properties ={
    val props = readFile(filePath)
    loadProperties(props)
  }

  def constructSparkConf(filePath:String): SparkConf = {
    val sparkConf = new SparkConf()
    for((k,v) <- loadSparkConf(filePath))
      sparkConf.set(k,v.toString)
    sparkConf
  }
}
