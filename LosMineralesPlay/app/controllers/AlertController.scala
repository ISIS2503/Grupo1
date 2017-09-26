package controllers

import javax.inject._
import javax.persistence.{EntityManager, EntityManagerFactory, Persistence}
import java.util.Date

import models.Alert
import play.api.mvc.{AbstractController, ControllerComponents, Result}

/**
  * Created by pedrosalazar on 25/9/17.
  */
@Singleton
class AlertController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("cassandra_pu")

  def persistTest: Result = {
    val em: EntityManager = emf.createEntityManager()
    val alert: Alert = new Alert("OUT OF RANGE",23243242, 2.2, 2.2, 2.2, 2.2, new Date)
    em.persist(alert)
    em.close()
    return Ok("Out of range alert at 23243242 persisted for persistence unit cassandra_pu")
  }

  def persist(alert: Alert): Result = {
    val em: EntityManager = emf.createEntityManager()
    em.persist(alert)
    em.close()
    return Ok("Out of range alert at " + alert.location + " persisted for persistence unit cassandra_pu")
  }

  def findTest: Result = {
    val em: EntityManager = emf.createEntityManager()
    val alert: Alert = em.createQuery("SELECT t FROM Alerts WHERE location = 23243242", classOf[Alert]).getSingleResult
    em.close()
    return Ok("Found Alert in database with the following location: " +  alert.location)
  }

  def findByTimestamp(date: Date): Result = {
    val em: EntityManager = emf.createEntityManager()
    val alert: Alert = em.createQuery("SELECT t FROM Alerts WHERE timestamp = '" + date + "'", classOf[Alert]).getSingleResult
    em.close()
    return Ok("Found Alert in database with the following timestamp: " +  alert.timestamp)
  }




}
