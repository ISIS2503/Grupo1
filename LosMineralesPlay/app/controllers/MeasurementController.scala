package controllers

import javax.inject._
import javax.persistence.{EntityManagerFactory, Persistence}
import java.util.Date
import models.Measurement
import play.api.mvc.{AbstractController, ControllerComponents, Result}
import play.api.libs.functional.syntax._
import play.api.libs.json._
/**
  * Created by pedrosalazar on 26/9/17.
  */
@Singleton
class MeasurementController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("cassandra_pu")

  def persistTest = Action {
    val em = emf.createEntityManager()
    val measurement = new Measurement(213423, 323.32, 32.32, 323.32, 323.32,"27/09/2017")
    em persist measurement
    em.close
    Ok("Measurement at 213423 persisted por persistence unit cassandra_pu")
  }

  implicit val readmes = (
    (__ \ 'location).read[Long] and
      (__ \ 'temperature).read[Double] and
      (__ \ 'gas).read[Double] and
      (__ \ 'ligth).read[Double] and
      (__ \ 'sound).read[Double] and
      (__ \ 'timestamp).read[String]
    ) tupled
  def persist = Action(parse.json) { request =>
    request.body.validate[(Long,Double,Double,Double,Double,String)].map { case (location,temperature,gas,ligth,sound,timestamp) =>
      //para hacer pruebas con la base de datos quitar el los comentarion
      //val em: EntityManager = emf.createEntityManager()
      val mes: Measurement = new Measurement(location,temperature,gas,ligth,sound,timestamp)
      //em persist mes
      //em.close
      Ok("measurment " + mes.location+";"+mes.timestamp+" record persisted for persistence unit cassandra_pu")
    }.getOrElse {
      BadRequest("Missing parameters")
    }
  }

  def findByLocation(loc: Int) = Action {
    val em = emf.createEntityManager()
    val measurements = em.createQuery("SELECT t FROM Measurements WHERE location = '" + loc + "'", classOf[Measurement]).getResultList
    em.close()
    Ok("Found " + measurements.size() + "measurements in database from the following location: " +  loc)
  }

  


}
