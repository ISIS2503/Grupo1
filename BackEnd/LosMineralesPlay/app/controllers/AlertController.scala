package controllers

import javax.inject._

import models.Alert
import models.Measurement
import play.api.mvc.{AbstractController, ControllerComponents}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json._, collection._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import models.JsonFormats._

import scala.concurrent.Future

/**
  * Created by pedrosalazar on 25/9/17.
  */
@Singleton
class AlertController @Inject()(val reactiveMongoApi: ReactiveMongoApi, cc: ControllerComponents)
  extends AbstractController(cc) with MongoController with ReactiveMongoComponents {

  def offlineAlerts: Future[JSONCollection] = database.map(
    _.collection[JSONCollection]("OfflineAlerts"))

  def outOfBoundsAlerts: Future[JSONCollection] = database.map(
    _.collection[JSONCollection]("OutOfBoundsAlerts"))

  def noChangeAlerts: Future[JSONCollection] = database.map(
    _.collection[JSONCollection]("NoChangeAlerts"))

  def createOfflineAlertFromJson = Action.async(parse.json) { request =>

    request.body.validate[Alert].map { alert =>

      offlineAlerts.flatMap(_.insert(alert)).map { lastError =>
        Created
      }
    }.getOrElse(Future.successful((BadRequest("invalid json"))))
  }

  def createOutOfBoundsAlertFromJson = Action.async(parse.json) { request =>

    request.body.validate[Alert].map { alert =>

      outOfBoundsAlerts.flatMap(_.insert(alert)).map { lastError =>
        Created
      }
    }.getOrElse(Future.successful((BadRequest("invalid json"))))
  }

  def createNoChangeAlertFromJson = Action.async(parse.json) { request =>

    request.body.validate[Alert].map { alert =>

      noChangeAlerts.flatMap(_.insert(alert)).map { lastError =>
        Created
      }
    }.getOrElse(Future.successful((BadRequest("invalid json"))))
  }

  def test = Action {
    Ok("alertas: [\n{\n    \"_id\" : ObjectId(\"5a1c34b06480bf\"),\n    \"alert_type\" : 2,\n    \"trigger\" : 1,\n    \"measurement\" : {\n        \"location\" : 100102201,\n        \"temperature\" : 223.3,\n        \"gas\" : 520.21,\n        \"light\" : 1250.54,\n        \"sound\" : 823.2,\n        \"timestamp\" : \" '\\\"2017-11-27T15\"\n    }\n},\n{\n    \"_id\" : ObjectId(\"5a1d0c9e92bf\"),\n    \"alert_type\" : 2,\n    \"trigger\" : 1,\n    \"measurement\" : {\n        \"location\" : 1001001,\n        \"temperature\" : 2223.3,\n        \"gas\" : 510.21,\n        \"light\" : 1503.54,\n        \"sound\" : 893.2,\n        \"timestamp\" : \" '\\\"2017-11-27T15\"\n    }\n},\n{\n    \"_id\" : ObjectId(\"5a1c349d0c9e92bf\"),\n    \"alert_type\" : 2,\n    \"trigger\" : 1,\n    \"measurement\" : {\n        \"location\" : 1001001,\n        \"temperature\" : 23.083,\n        \"gas\" : 507.21,\n        \"light\" : 1504.54,\n        \"sound\" : 8453.2,\n        \"timestamp\" : \" '\\\"2017-11-27T15\"\n    }\n}\n\n]")
  }

  def offlineTest(date: String) = Action {
    println("offline" + date)
    Ok("hola")
  }

  def outOfBoundsTest(date: String) = Action {
    println("outofbounds")
    Ok("hola")
  }

  def noChangeTest(date: String) = Action {
    println("nochange")
    Ok("hola")
  }


}


