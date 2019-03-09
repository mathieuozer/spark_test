import case_classes.CityBikeStation
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.scalatest.FlatSpec
import parser.PropertiesInitializer.{constructSparkConf, getProperties}

class MainSpec extends FlatSpec{

  var filePath = "src/test/resources/init.properties"
  val props = getProperties(filePath)
  val sparkConf = constructSparkConf(filePath)
  val sparkSession = SparkSession.builder.master(props.getProperty("spark.master"))
    .appName("City Bike Clustering").config(sparkConf)
    .getOrCreate
  val mdf: DataFrame = sparkSession.read.option("multiline",true)
    .json(props.getProperty("file.path"))

  import sparkSession.implicits._

  val bikeStationDf = mdf map {row =>
    CityBikeStation(
      number = row.getAs[Long]("number"),
      name = row.getAs[String]("name"),
      address = row.getAs[String]("address"),
      latitude = row.getAs[Double]("latitude"),
      longitude = row.getAs[Double]("longitude"))
  }

  "A Dataframe" should "be initialized correctly from json file" in {
    assert(mdf.schema.size == 5)
  }

  "A Dataframe" should "be converted correctly from to a dataset" in {
    assert(bikeStationDf.schema.size == 5)
    println(bikeStationDf.getClass)
  }
}
