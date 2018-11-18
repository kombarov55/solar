package controllers

import javax.inject.Inject
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}
import service.bs.TagService

class SearchController @Inject()(private val tagService: TagService, cc: ControllerComponents) extends AbstractController(cc) {

  def search() = Action { implicit rq =>
    val tags = List("java")

//    tagService.request(tags(0)).map { tags =>
//      val json = Json.toJson(tags)
//      val prettyPrinted = Json.prettyPrint(json)
//
//      Ok(prettyPrinted)
//    }

    Ok("success")
  }

}
