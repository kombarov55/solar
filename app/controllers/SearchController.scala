package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.libs.json._

class SearchController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def search() = Action { implicit rq =>
    rq.uri

    val json = """{ "result": "ok" }"""
    Ok(Json.toJson(json))
  }

}
