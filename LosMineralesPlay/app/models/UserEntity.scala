package models;

import javax.persistence._;

/**
  * Created by df.castro12 on 24/09/2017.
  */
@Entity
@Table(name = "users")
class UserEntity(id: Int,usr: String,psswrd: Long, email: String) {

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
  var email: String = email
}
