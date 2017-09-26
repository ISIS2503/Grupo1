package controllers

import javax.inject._
import javax.persistence._
import models.User
import play.api.mvc._

/**
  * Created by df.castro12 on 25/09/2017.
  */

@Singleton
class UserController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("cassandra_pu")

  def persistTest = Action {
    val em: EntityManager = emf.createEntityManager()
    val user: User = new User("test01", 32323, "test@test.com")
    em.persist(user)
    em.close()
    Ok("user test01 record persisted for persistence unit cassandra_pu")
  }

  def persist(user: User) = Action {
    val em: EntityManager = emf.createEntityManager()
    em.persist(user)
    em.close()
    Ok("user" + user.username + "record persisted for persistence unit cassandra_pu")
  }

  def findTest = Action {
    val em: EntityManager = emf.createEntityManager()
    val user: User = em.createQuery("SELECT t FROM Users WHERE username = 'test01'", classOf[User]).getSingleResult
    em.close()
    Ok("Found User in database with the following details: " + printUser(user))
  }

  def findById(id: Int) = Action {
    val em: EntityManager = emf.createEntityManager()
    val user: User = em.find(classOf[User], id)
    em.close()
    Ok("Found User in database with the following details: " + printUser(user))
  }

  def findByUsername(username: String) = Action {
    val em: EntityManager = emf.createEntityManager()
    val user: User = em.createQuery("SELECT t FROM Users WHERE username = '" + username + "'", classOf[User]).getSingleResult
    em.close()
    Ok("Found User in database with the following details: " + printUser(user))
  }

  def updateTest = Action {
  val em: EntityManager = emf.createEntityManager()
    var user: User = em.createQuery("SELECT t FROM Users WHERE name = 'test01'", classOf[User]).getSingleResult
    user.setEmail("CAMBIEELMAIL@HOLA.COM ")
    em.merge(user)
    user = em.createQuery("SELECT t FROM Users WHERE name = 'test01'", classOf[User]).getSingleResult
    Ok("Record updated: " + printUser(user))
  }

  def delete(user: User) = Action {
    val em = emf.createEntityManager()
    val user1 = user
    em.remove(user1)
    Ok("Record deleted: " + printUser(user))
  }

  def printUser(user: User): String = {
    if (user == null) return "Record not found"
    "\n------------------------------" + "\nUser ID: " + user.id + "\nName: " + user.username + "\nPassword: " + user.password + "\nEmail: " + user.email
  }
}
