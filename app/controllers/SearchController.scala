package controllers

import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import service.bs.TagService

class SearchController @Inject()(private val tagService: TagService, cc: ControllerComponents) extends AbstractController(cc) {

  def search() = Action.async { implicit rq =>
    val tags = getTagsFromUrl(rq.uri)

    import scala.concurrent.ExecutionContext.Implicits.global

    tagService.countTags(tags).map { result =>
      val json = Json.toJson(result)
      val prettyPrinted = Json.prettyPrint(json)
      Ok(prettyPrinted)
    }
  }

  /**
    * Получить список тегов из url.
    *
    * @param url url.
    * @return список запрашиваемых url.
    */
  private def getTagsFromUrl(url: String = "/search?tag=clojure&tag=java"): Seq[String] = {
    return url
      .substring(url.indexOf("?") + 1)
      .split("&")
      .map { s =>
        val signIndex = s.indexOf("=")
        val value = s.substring(signIndex + 1)

        value
      }
  }


}
