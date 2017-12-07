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
    Ok("measurements: [\n{\n    \"_id\" : ObjectId(\"5a1c2f2e9d0c9e8665\"),\n    \"location\" : 1001001,\n    \"temperature\" : 3232.3,\n    \"gas\" : 5033.21,\n    \"light\" : 15220.54,\n    \"sound\" : 8223.2,\n    \"timestamp\" : \"\\\"2017-11-27T15:28:42.010Z\\\"\"\n},\n{\n    \"_id\" : ObjectId(\"5a1c2f2964804dadae9d0c9e8665\"),\n    \"location\" : 10011001,\n    \"temperature\" : 23.3,\n    \"gas\" : 5440.21,\n    \"light\" : 12350.54,\n    \"sound\" : 84433.2,\n    \"timestamp\" : \"\\\"2017-11-27T15:28:42.010Z\\\"\"\n},\n{\n    \"_id\" : ObjectId(\"5a1c2f296e8665\"),\n    \"location\" : 103301001,\n    \"temperature\" : 232323.3,\n    \"gas\" : 5055.21,\n    \"light\" : 15660.54,\n    \"sound\" : 833.2,\n    \"timestamp\" : \"\\\"2017-11-27T15:28:42.010Z\\\"\"\n}]")
  }
}
