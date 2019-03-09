package cluster_calculator

import case_classes.CityBikeStation
import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.{Dataset, SparkSession}
import parser.PropertiesInitializer

object KMeansCalculator {

  val props = PropertiesInitializer.properties

  def calculateKMeans(dataset:Dataset[CityBikeStation], ss:SparkSession): Unit = {
    val numClusters = props.getProperty("number.cluster").toInt
    val numIterations = props.getProperty("number.iterations").toInt
    val assembler = new VectorAssembler().setInputCols(Array("latitude", "longitude")).setOutputCol("features")

    val kmeans = new KMeans().setK(numClusters)
                    .setMaxIter(numIterations)
                    .setSeed(1L)

    val output = assembler.transform(dataset)

    val model = kmeans.fit(output)

    // Evaluate clustering by computing Within Set Sum of Squared Errors.
    val WSSSE = model.computeCost(output)
    println(s"Within Set Sum of Squared Errors = $WSSSE")

    // Shows the result.
    println("Cluster Centers: ")
    model.clusterCenters.foreach(println)
    model.write.overwrite.save(props.getProperty("output.path"))

  }

}
