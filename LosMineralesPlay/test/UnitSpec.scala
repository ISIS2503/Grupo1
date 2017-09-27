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

//  "MeasurementController" should {
//    "return a valid result on action.persistenceTest" in {
//      val controller = new MeasurementController(stubControllerComponents())
//      val result = controller.persistTest(FakeRequest())
//      contentAsString(result) must be("Measurement at 213423 persisted por persistence unit cassandra_pu")
//    }
//
//    "return a valid result on action.findTest" in {
//      val controller = new MeasurementController(stubControllerComponents())
//      val result = controller.findByLocation(0)
//      //val m = controller.measurement()
//     // contentAsString(result) must contain ("Found measurement in database at the following location:" + m.location)
//      contentAsString(result) should contain ("Found measurement in database at the following location:")
//    }
//  }




}
