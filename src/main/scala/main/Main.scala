package main

import case_classes.CityBikeStation
import cluster_calculator.KMeansCalculator
import org.apache.spark.sql.{DataFrame, SparkSession}
import parser.CityBikeStationParser._
import parser.PropertiesInitializer._

object Main extends App {

  var confFile = "src/main/resources/init.properties"
  if (args.length == 1)
    confFile = args(0)

  val props = getProperties(confFile)
  val sparkConf = constructSparkConf(confFile)

  val sparkSession = SparkSession.builder.master(props.getProperty("spark.master"))
                                          .appName("City Bike Clustering").config(sparkConf)
                                          .getOrCreate
  import sparkSession.implicits._

  //read and clean our json file
  println(props.getProperty("file.path"))
  val mdf: DataFrame = sparkSession.read.option("multiline",true)
                              .json(props.getProperty("file.path"))
                              .repartition(props.getProperty("number.partition").toInt)
/*
  println("Dataframe schema:")
  mdf.printSchema()
*/

  val bikeStationDf = mdf map {row =>
    CityBikeStation(
      number = row.getAs[Long]("number"),
      name = row.getAs[String]("name"),
      address = row.getAs[String]("address"),
      latitude = row.getAs[Double]("latitude"),
      longitude = row.getAs[Double]("longitude"))
  } map cleanColumns

  /*
  println("test 1")
  println("Dataset schema:")
  bikeStationDf.printSchema()
  println("test 2")
  */
  KMeansCalculator.calculateKMeans(bikeStationDf,sparkSession)

}


