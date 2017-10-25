package controllers

import javax.inject._

import models.Measurement
import play.api.mvc.{AbstractController, ControllerComponents}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json._, collection._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import models.JsonFormats._

import scala.concurrent.Future

/**
  * Created by pedrosalazar on 26/9/17.
  */

@Singleton
class MeasurementController @Inject()(val reactiveMongoApi: ReactiveMongoApi, cc: ControllerComponents)
  extends AbstractController(cc) with MongoController with ReactiveMongoComponents {

  def collection: Future[JSONCollection] = database.map(
    _.collection[JSONCollection]("measurements"))

  def createFromJson = Action.async(parse.json) { request =>
    /*
     * request.body is a JsValue.
     * There is an implicit Writes that turns this JsValue as a JsObject,
     * so you can call insert() with this JsValue.
     * (insert() takes a JsObject as parameter, or anything that can be
     * turned into a JsObject using a Writes.)
     */
    request.body.validate[Measurement].map { mes =>
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
