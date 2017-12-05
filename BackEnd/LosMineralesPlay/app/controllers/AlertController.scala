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
    println("holaaaa")
    Ok("hola")
  }
}


