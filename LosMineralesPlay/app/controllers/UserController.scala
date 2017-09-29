package controllers

import javax.inject._

import models.User
//import models.User
import play.api.mvc.{AbstractController, ControllerComponents}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json._, collection._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import models.JsonFormats._

import scala.concurrent.Future


/**
  * Created by df.castro12 on 25/09/2017.
  */
@Singleton
class UserController @Inject()(val reactiveMongoApi: ReactiveMongoApi, cc: ControllerComponents)
  extends AbstractController(cc) with MongoController with ReactiveMongoComponents {

  def collection: Future[JSONCollection] = database.map(
    _.collection[JSONCollection]("users"))

  def createFromJson = Action.async(parse.json) { request =>
    /*
     * request.body is a JsValue.
     * There is an implicit Writes that turns this JsValue as a JsObject,
     * so you can call insert() with this JsValue.
     * (insert() takes a JsObject as parameter, or anything that can be
     * turned into a JsObject using a Writes.)
     */
    request.body.validate[User].map { mes =>
      // `mes` is an instance of the case class `models.Measurement`
      collection.flatMap(_.insert(mes)).map { lastError =>
        Created
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def test = Action {
    println("holaaaa")
    Ok("hola")
  }
}
//class UserController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
//
//  //  def this(){
//  //    this()
//  //  }
//
//  val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("cassandra_pu")
//
//  def persistTest = Action {
//    val em: EntityManager = emf.createEntityManager()
//    val user: User = new User("test01", 32323, "test@test.com")
//    em.persist(user)
//    em.close()
//    Ok("user test01 record persisted for persistence unit cassandra_pu")
//  }
//
//  def persistTemp(user: User) = Action {
//    val em: EntityManager = emf.createEntityManager()
//    em.persist(user)
//    em.close()
//    Ok("user record persisted for persistence unit cassandra_pu test")
//  }
//
//  //Ejemplo uso body
//  implicit val rds = (
//    (__ \ 'name).read[String] and
//      (__ \ 'lastname).read[String]
//    ) tupled
//
//  def sayHello = Action(parse.json) { request =>
//    request.body.validate[(String, String)].map { case (name, lastname) =>
//      Ok("Hello " + name + "," + lastname)
//    }.getOrElse {
//      BadRequest("Missing parameters")
//    }
//  }
//
//  implicit val readuser = (
//    (__ \ 'username).read[String] and
//      (__ \ 'password).read[Long] and
//      (__ \ 'email).read[String]
//    ) tupled
//
//  def persist = Action(parse.json) { request =>
//    request.body.validate[(String, Long, String)].map { case (username, password, email) =>
//      //para hacer pruebas con la base de datos quitar el los comentarion
//      val em: EntityManager = emf.createEntityManager()
//      val user: User = new User(username, password, email)
//      em.persist(user)
//      em.close()
//      Ok("user " + user.username + " record persisted for persistence unit cassandra_pu")
//    }.getOrElse {
//      BadRequest("Missing parameters")
//    }
//  }
//
//  def findTest = Action {
//    val em: EntityManager = emf.createEntityManager()
//    val user: User = em.createQuery("SELECT t FROM Users WHERE username = 'test01'", classOf[User]).getSingleResult
//    em.close()
//    Ok("Found User in database with the following details: " + printUser(user))
//  }
//
//  def findById(id: Int) = Action {
//    val em: EntityManager = emf.createEntityManager()
//    val user: User = em.find(classOf[User], id)
//    em.close()
//    Ok("Found User in database with the following details: " + printUser(user))
//  }
//
//  def findByUsername(username: String) = Action {
//    val em: EntityManager = emf.createEntityManager()
//    val user: User = em.createQuery("SELECT t FROM Users WHERE username = '" + username + "'", classOf[User]).getSingleResult
//    em.close()
//    Ok("Found User in database with the following details: " + printUser(user))
//  }
//
//  def updateTest = Action {
//    val em: EntityManager = emf.createEntityManager()
//    var user: User = em.createQuery("SELECT t FROM Users WHERE name = 'test01'", classOf[User]).getSingleResult
//    user.setEmail("CAMBIEELMAIL@HOLA.COM ")
//    em.merge(user)
//    user = em.createQuery("SELECT t FROM Users WHERE name = 'test01'", classOf[User]).getSingleResult
//    Ok("Record updated: " + printUser(user))
//  }
//
//
//  def delete(user: User) = Action {
//    val em = emf.createEntityManager()
//    val user1 = user
//    em.remove(user1)
//    Ok("Record deleted: " + printUser(user))
//  }
//
//  def printUser(user: User): String = {
//    if (user == null) return "Record not found"
//    "\n------------------------------" + "\nUser ID: " + user.id + "\nName: " + user.username + "\nPassword: " + user.password + "\nEmail: " + user.email
//  }
//}
