package controllers

import javax.inject._

import models.Alert
import play.api.mvc.{AbstractController, ControllerComponents}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json._, collection._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import models.JsonFormats._

import scala.concurrent.Future

@Singleton
class AlertController @Inject()(val reactiveMongoApi: ReactiveMongoApi, cc: ControllerComponents)
  extends AbstractController(cc) with MongoController with ReactiveMongoComponents {

  def collection: Future[JSONCollection] = database.map(
    _.collection[JSONCollection]("alerts"))

  def createFromJson = Action.async(parse.json) { request =>
    /*
     * request.body is a JsValue.
     * There is an implicit Writes that turns this JsValue as a JsObject,
     * so you can call insert() with this JsValue.
     * (insert() takes a JsObject as parameter, or anything that can be
     * turned into a JsObject using a Writes.)
     */
    request.body.validate[Alert].map { mes =>
      // `mes` is an instance of the case class `models.Alert`
      collection.flatMap(_.insert(mes)).map { lastError =>
        Created
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def test = Action {
    println("holaaaa")
    Ok("hola")
  }
//class AlertController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
//
//  val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("cassandra_pu")
//
//  def persistTest = Action {
//    val em = emf.createEntityManager()
//    val alert = new Alert("OUT OF RANGE", 23243242, 2.2, 2.2, 2.2, 2.2, "olakeac")
//    em persist alert
//    em.close()
//    Ok("Out of range alert at 23243242 persisted for persistence unit cassandra_pu")
//  }
//
//  def persist(alert: Alert) = Action {
//    val em = emf.createEntityManager()
//    em persist alert
//    em.close()
//    Ok("Out of range alert at " + alert.location + " persisted for persistence unit cassandra_pu")
//  }
//
//  def findTest = Action {
//    val em = emf.createEntityManager()
//    val alert = em.createQuery("SELECT t FROM Alerts WHERE location = 23243242", classOf[Alert]).getSingleResult
//    em.close()
//    Ok("Found Alert in database with the following location: " + alert.location)
//  }
//
//  def findByTimestamp(date: Date) = Action {
//    val em = emf.createEntityManager()
//    val alert = em.createQuery("SELECT t FROM Alerts WHERE timestamp = '" + date + "'", classOf[Alert]).getSingleResult
//    em.close()
//    Ok("Found Alert in database with the following timestamp: " + alert.timestamp)
//  }
//
//  def updateTest = Action {
//    val em = emf.createEntityManager()
//    var alert = em.createQuery("SELECT t FROM Alerts WHERE location = 23243242", classOf[Alert]).getSingleResult
//    alert.setGas(9999)
//    em merge alert
//    alert = em.createQuery("SELECT t FROM Alerts WHERE location = 23243242", classOf[Alert]).getSingleResult
//    Ok("Record updated: " + printAlert(alert))
//  }
//
//  def delete(alert: Alert) = Action {
//    val em = emf.createEntityManager()
//    val alert1 = alert
//    em remove alert1
//    Ok("Record deleted: " + printAlert(alert))
//  }
//
//  def printAlert(alert: Alert): String = {
//    if (alert == null) return "Record not found"
//    "\n------------------------------" + "\nAlert ID: " + alert.id + "\nType: " + alert.alertType + "\nLocation: " + alert.location + "\nTimestamp: " + alert.timestamp
//  }
//
//
//}
