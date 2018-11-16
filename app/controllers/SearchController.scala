package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.libs.json._
import service.TagService

class SearchController @Inject()(private val tagService: TagService, cc: ControllerComponents) extends AbstractController(cc) {

  def search() = Action { implicit rq =>
    rq.uri

    val json = tagService.stub
    Ok(Json.prettyPrint(Json.toJson(json)))
  }

}
