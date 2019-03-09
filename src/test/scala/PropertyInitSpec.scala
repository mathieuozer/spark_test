import org.scalatest.FlatSpec
import parser.PropertiesInitializer._

class PropertyInitSpec extends FlatSpec{
  var filePath = "src/test/resources/init.properties"

  "A PropertyInitSpec" should "init correctly the map" in {
    val m = readFile(filePath)
    assert(m.get("spark.master").equals(Some("local")))
    assert(m.get("output.path").equals(Some("src/test/resources/KMeansModel")))
    assert(m.get("file.path").equals(Some("src/test/resources/Brisbane_CityBike.json")))
    assert(m.get("number.iterations").equals(Some("20")))
    assert(m.get("number.cluster").equals(Some("2")))
    assert(m.size == 7)
  }

  "A PropertyInitSpec" should "init correctly spark conf map" in {
    val m = loadSparkConf(filePath)
    assert(m.get("spark.master").equals(Some("local")))
    assert(m.size == 1)
  }
}
