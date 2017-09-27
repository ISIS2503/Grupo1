import akka.actor.ActorSystem
import controllers.{AsyncController, CountController, UserController, AlertController, MeasurementController}
import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test.FakeRequest
import java.util.Date

/**
 * Unit tests can run without a full Play application.
 */
class UnitSpec extends PlaySpec {

  "CountController" should {

    "return a valid result with action" in {
      val controller = new CountController(stubControllerComponents(), () => 49)
      val result = controller.count(FakeRequest())
      contentAsString(result) must equal("49")
    }
  }

  "AsyncController" should {

    "return a valid result on action.async" in {
      // actor system will create threads that must be cleaned up even if test fails
      val actorSystem = ActorSystem("test")
      try {
        implicit val ec = actorSystem.dispatcher
        val controller = new AsyncController(stubControllerComponents(), actorSystem)
        val resultFuture = controller.message(FakeRequest())
        contentAsString(resultFuture) must be("Hi!")
      } finally {
        // always shut down actor system at the end of the test.
        actorSystem.terminate()
      }
    }

  }

  "MeasurementController" should {
    "return a valid result on action.persistenceTest" in {
      val controller = new MeasurementController(stubControllerComponents())
      val result = controller.persistenceTest(FakeRequest())
      contentAsString(result) must be("Measurement at 213423 persisted por persistence unit cassandra_pu")
    }

    "return a valid result on action.findTest" in {
      val controller = new MeasurementController(stubControllerComponents())
      val result = controller.findTest(FakeRequest())
      //val m = controller.measurement()
     // contentAsString(result) must contain ("Found measurement in database at the following location:" + m.location)
      contentAsString(result) should contain ("Found measurement in database at the following location:")
    }
  }

  "AlertController" should {
    "return a valid result on action.persist" in {
      val controller = new AlertController(stubControllerComponents())
      val alert = new Alert("OUT OF RANGE", 99999999, 2.2, 2.2, 2.2, 2.2, new Date)
      val result = controller.persist(alert)
     // contentAsString(result) must be ("Out of range alert at " + alert.location + " persisted for persistence unit cassandra_pu")
      status(result) must equalTo(OK)
    }

    "return a valid result on action.findByTimestamp" in {
      val day = new Date
      val alert = new Alert("OUT OF RANGE", 99999998, 2.2, 2.2, 2.2, 2.2, day)
      val controller = new AlertController(stubControllerComponents())
      controller.persist(alert)
      val result = controller.findByTimestamp(day)
      //contentAsString(result) must be("Found Alert in database with the following timestamp: " + alert.timestamp)
      status(result) must equalTo(OK)
    }

    "return a valid result on action.delete" in {
      val alert = new Alert("OUT OF RANGE", 99999997, 2.2, 2.2, 2.2, 2.2, new Date)
      val controller = new AlertController(stubControllerComponents())
      controller.persist(alert)
      val result = controller.delete(alert)
      status(result) must equalTo(OK)
    }
  }
  "UserController" should {
    "return a valid result action.persistTemp" in {
      val user = new User("Test1",99999,"unit@test.com")
      val controller = new UserController(stubControllerComponents())
      val result = controller.persistTemp(user)
      status(result) must equalTo(Ok)
    }
    "return a valid result action.findById" in {
      val user = new User("Test2",99998,"unit@test.com")
      val controller = new UserController(stubControllerComponents())
      controller.persistTemp(user)
      val result = controller.findById(99998)
      status(result) must equalTo(Ok)
    }
    "return a valid result action.findByName" in {
      val user = new User("Test3",99997,"unit@test.com")
      val controller = new UserController(stubControllerComponents())
      controller.persistTemp(user)
      val result = controller.findByName("Test3")
      status(result) must equalTo(Ok)
    }
    "return a valid result action.delete" in {
      val user = new User("Test4",99996,"unit@test.com")
      val controller = new UserController(stubControllerComponents())
      controller.persistTemp(user)
      val result = controller.delete(99996)
      status(result) must equalTo(Ok)
    }

  }



}
