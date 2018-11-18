package controllers

import dto.CountDto
import javax.inject.Inject
import util.FixedSizeExecutionContext
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}
import service.bs.TagService
import play.api.libs.functional.syntax._


/**
  * Контроллер получения данных по подсчёту тегов.
  * @param tagService сервис с логикой работы.
  * @param fixedSizeExecutionContext executionContext, использующийся для параллелизма.
  */
class SearchController @Inject()(
    private val tagService: TagService,
    private implicit val fixedSizeExecutionContext: FixedSizeExecutionContext,
    cc: ControllerComponents
) extends AbstractController(cc) {

  def search() = Action.async { implicit rq =>
    val requestTags = getTagsFromUrl(rq.uri)

    tagService.getTagCounts(requestTags).map { result =>
      val json = Json.toJson(result)
      val prettyPrinted = Json.prettyPrint(json)
      Ok(prettyPrinted).as(JSON)
    }
  }

  /**
    * Получить список тегов из url.
    *
    * @param url url.
    * @return список запрашиваемых url.
    */
  private def getTagsFromUrl(url: String): Seq[String] = {
    url
      .substring(url.indexOf("?") + 1)
      .split("&")
      .map { s =>
        val signIndex = s.indexOf("=")
        val value = s.substring(signIndex + 1)

        value
      }
  }

  private implicit val countDtoWrites: Writes[CountDto] = (
    (JsPath \ "total").write[Int] and
      (JsPath \ "answered").write[Int]
    ) (unlift(CountDto.unapply))
}
