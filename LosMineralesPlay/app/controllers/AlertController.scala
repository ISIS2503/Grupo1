package controllers

import javax.inject._

import play.api.mvc.{AbstractController, ControllerComponents}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.Future

/**
  * Created by pedrosalazar on 25/9/17.
  */
@Singleton
class AlertController @Inject()(val reactiveMongoApi: ReactiveMongoApi, cc: ControllerComponents)
  extends AbstractController(cc) with MongoController with ReactiveMongoComponents {

  def collection: Future[JSONCollection] = database.map(
    _.collection[JSONCollection]("alerts"))

  )

  def createFromJson = Action.async(parse.json) { request =>

    request.body.validate[Alert].map { alert =>

      collection.flatMap(_.insert(alert)).map { lastError =>
        Created
      }
    }.getOrElse(Future.successful((BadRequest("invalid json"))))
  }
}
