import case_classes.CityBikeStation
import org.scalatest.FlatSpec
import parser.CityBikeStationParser

class CityBikeStationParserSpec extends FlatSpec{

  "A CityBikeStationParser" should "parse correctly the name" in {
   val cb = CityBikeStation(
      122,
      " mehmet ",
      "3 rte de ",
      42.2,
      45.6
    )

    val ccb = CityBikeStationParser.cleanColumns(cb)
    assert(!ccb.name.equals(cb.name))
    assert(ccb.name.equals("mehmet"))
  }

  "A CityBikeStationParser" should "parse correctly the adresse" in {
    val cb = CityBikeStation(
      122,
      " mehmet",
      " 3 rte de ",
      42.2,
      45.6
    )

    val ccb = CityBikeStationParser.cleanColumns(cb)
    assert(ccb.address != cb.address)
    assert(ccb.address.equals("3 rte de"))

  }

}
