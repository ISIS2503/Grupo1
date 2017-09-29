package models


case class User(name: String, password: Long, mail: String)
//import javax.persistence._
//
//import scala.beans.BeanProperty
//
///**
//  * Created by df.castro12 on 24/09/2017.
//  */

//
//@Entity
//@Table(name = "Users", schema = "LosMinerales@cassandra_pu")
//class User(usr: String, psswrd: Long, mail: String) {
//
//  //def this() {
//  //  this
//  //}
//
//  @Id
//  @GeneratedValue(strategy = GenerationType.AUTO)
//  @BeanProperty
//  var id: Int = _
//
//  @Column(name = "username")
//  @BeanProperty
//  var username: String = usr
//
//  @Column(name = "password")
//  @BeanProperty
//  var password: Long = psswrd
//
//  @Column(name = "email")
//  @BeanProperty
//  var email: String = mail
//
//
//  def this() {
//    this("", 0, "")
//  }
//}
