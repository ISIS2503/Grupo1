package models;

import javax.persistence._
import org.apache.commons.configuration.beanutils.BeanFactory

/**
  * Created by df.castro12 on 24/09/2017.
  */
@Entity
@Table(name = "users")
class UserEntity(usr: String,psswrd: Long, mail: String) {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanFactory
  var id: Int = _

  @Column(name="username")
  @BeanFactory
  var username: String = usr

  @Column(name="password")
  @BeanFactory
  var password: Long = psswrd

  @Column(name="email")
  @BeanFactory
  var email: String = mail
}
