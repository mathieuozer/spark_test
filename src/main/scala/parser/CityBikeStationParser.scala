package parser

import case_classes.CityBikeStation

object CityBikeStationParser {

  def cleanColumns(cleanedStation: CityBikeStation): CityBikeStation = {
    CityBikeStation(cleanedStation.number,
      cleanedStation.name.trim,
      cleanedStation.address.trim,
      cleanedStation.latitude,
      cleanedStation.longitude)
  }
}
