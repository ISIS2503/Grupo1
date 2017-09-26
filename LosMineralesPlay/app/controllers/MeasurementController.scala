package controllers

import javax.inject._
import javax.persistence.{EntityManagerFactory, Persistence}
import java.util.Date
import models.Measurement
import play.api.mvc.{AbstractController, ControllerComponents, Result}

/**
  * Created by pedrosalazar on 26/9/17.
  */
@Singleton
class MeasurementController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("cassandra_pu")

  def persistTest = Action {
    val em = emf.createEntityManager()
    val measurement = new Measurement(213423, 323.32, 32.32, 323.32, 323.32, new Date())
    em persist measurement
    em.close
    Ok("Measurement at 213423 persisted por persistence unit cassandra_pu")
  }

  def Persist(measurement: Measurement) = Action {
    val em = emf.createEntityManager()
    em persist measurement
    em.close
    Ok("Measurement at 213423 persisted por persistence unit cassandra_pu")
  }

//  def findTest: Result = {
//    val em = emf.createEntityManager()
//    val measurement = em.createQuery("SELECT t FROM Measurements WHERE ")
//  }



}
