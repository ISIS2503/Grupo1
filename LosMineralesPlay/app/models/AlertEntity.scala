package models

import javax.persistence._
import java.util.Date
import scala.beans.BeanProperty

/**
  * Created by pedrosalazar on 25/9/17.
  */

@Entity
@Table(name = "AlertHistory")
class AlertEntity(alertTypeP: String, pplace: Long, temp: Double, pppm: Double, lux: Double, decibels: Double, pTimestamp: Date) {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = _

  @Column(name = "alertType")
  @BeanProperty
  var alertType: String = alertTypeP

  @Column(name = "location")
  @BeanProperty
  var location = pplace

  @Column(name = "temperature")
  @BeanProperty
  var temperature = temp

  @Column(name = "gas")
  @BeanProperty
  var gas = pppm

  @Column(name = "light")
  @BeanProperty
  var light = lux

  @Column(name = "sound")
  @BeanProperty
  var sound = decibels

  @Column(name = "timestamp")
  @BeanProperty
  var timestamp = pTimestamp
}
