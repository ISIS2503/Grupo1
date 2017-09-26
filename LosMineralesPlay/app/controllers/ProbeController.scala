package controllers

import javax.inject._
import javax.persistence.{EntityManagerFactory, Persistence}
import java.util.Date
import models.Probe
import play.api.mvc.{AbstractController, ControllerComponents, Result}

/**
  * Created by pedrosalazar on 26/9/17.
  */
@Singleton
class ProbeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("cassandra_pu")

  def persistTest: Result = {
    val em = emf.createEntityManager()
    val probe = new Probe(213423, 323.32, 32.32, 323.32, 323.32, new Date())
    em persist probe
    em.close
    Ok("Probe at 213423 persisted por persistence unit cassandra_pu")
  }

  def Persist(probe: Probe): Result = {
    val em = emf.createEntityManager()
    em persist probe
    em.close
    Ok("Probe at 213423 persisted por persistence unit cassandra_pu")
  }

  def findTest: Result = {
    
  }



}
