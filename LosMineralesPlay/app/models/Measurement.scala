package models

import javax.persistence._

import java.util.Date

import scala.beans.BeanProperty

@Entity
@Table(name = "Measurements", schema = "LosMinerales@cassandra_pu")
class Measurement(plocation: Long,temp: Double, partsPerM: Double, lux: Double, dec: Double, time: String ) {



  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty
  var id: Int = _

  @Column(name="location")
  @BeanProperty
  var location: Long = plocation

  @Column(name="temperature")
  @BeanProperty
  var temperature: Double = temp

  @Column(name="gas")
  @BeanProperty
  var gas: Double = partsPerM

  @Column(name="light")
  @BeanProperty
  var light: Double = lux

  @Column(name="sound")
  @BeanProperty
  var sound: Double = dec

  @Column(name="timestamp")
  @BeanProperty
  var timestamp: String = time

  def this(){
    this(0,0.0,0.0,0.0,0.0,"");
  }
}
