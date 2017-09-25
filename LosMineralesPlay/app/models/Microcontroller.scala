package models;

import javax.persistence._
import org.apache.commons.configuration.beanutils.BeanFactory
import java.util.Date

@Entity
@Table(name = "microcontroller")
class Microcontroller(temp: double, partsPerM: double, lux: double, dec: double, time: Date ) {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanFactory
  var id: Int = _

  @Column(name="temperture")
  @BeanFactory
  var temperture: double = temp

  @Column(name="gas")
  @BeanFactory
  var gas: double = ppm

  @Column(name="light")
  @BeanFactory
  var light: double = lux

  @Column(name="sound")
  @BeanFactory
  var sound: double = dec

  @Column(name="timestap")
  @BeanFactory
  var timestap: Date = time
}
