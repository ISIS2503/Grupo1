package models

import javax.persistence._
import java.util.Date

import scala.beans.BeanProperty

/**
  * Created by pedrosalazar on 25/9/17.
  */

@Entity
@Table(name = "AlertHistory")
class AlertEntity(alertTypeP: String, place: Long, temperatura: Double, ppm: Double, lux: Double, decibels: Double, timestamp: Date ) {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = _

  @Column(name = "alertType")
  @BeanProperty
  var alertType: String  = alertTypeP


}
