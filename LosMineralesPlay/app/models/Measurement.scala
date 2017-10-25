package models


case class Measurement(location: Long, temperature: Double, gas: Double,
                       light: Double, sound: Double, timestamp: String)
