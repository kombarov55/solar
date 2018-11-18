package service.bs

import dto.{QuestionDto, CountDto}
import javax.inject.Singleton
import play.api.libs.json._

import scala.collection.mutable

/**
  * Функции для получения данных из json.
  */
@Singleton
class JsonExtracter {

  /**
    * Посчитать количество встречаемых тегов в ответе.
    *
    * @param response ответ.
    * @return map из тега -> количество их в ответе.
    */
  def countTags(response: JsValue): Map[String, CountDto] = {
    (response \ "items").as[JsArray].value
      .map { jsValue => jsObjectToQuestionDto(jsValue.as[JsObject]) }
      .foldLeft(mutable.Map[String, CountDto]()) { (acc, dto) => accumulate(acc, dto) }
      .toMap
  }

  /**
    * Просуммировать все количества тегов.
    *
    * @param maps список количств тегов.
    * @return просуммированное количество тегов.
    */
  def mergeResults(maps: List[Map[String, CountDto]]): Map[String, CountDto] = {
    val resultMap = mutable.Map[String, CountDto]()

    for (map <- maps) {
      map.foreach { case (name, CountDto(count, answered)) =>
        accumulate(resultMap, name, count, answered)
      }
    }

    resultMap.toMap
  }

  /**
    * Увеличить счётчик внутри QuestionDto.
    *
    * @param map  map.
    * @param name имя ключа.
    * @param count количество ответов с переданным тегом.
    * @param answered количество отвеченных вопросов с переданным тегом.
    */
  private def accumulate(map: mutable.Map[String, CountDto], name: String, count: Int, answered: Int): Unit = {
    val prevValue = map.get(name)
    if (prevValue.isDefined) {
      prevValue.get.count += count
      prevValue.get.answered += answered
    } else {
      map.put(name, CountDto(count, answered))
    }
  }

  /**
    * Применить accumulate к QuestionDto.
    */
  private def accumulate(map: mutable.Map[String, CountDto], questionDto: QuestionDto): mutable.Map[String, CountDto] = {
    val (tagNames, answered) = QuestionDto.unapply(questionDto).get

    for (tagName <- tagNames) {
      accumulate(map, tagName, 1, if (answered) 1 else 0)
    }

    map
  }

  /**
    * Преобразование JsObject в QuestionDto.
    *
    * @param jsObject JsObject.
    * @return QuestionDto.
    */
  private def jsObjectToQuestionDto(jsObject: JsObject): QuestionDto = {
    val tags = jsObject.value("tags").as[JsArray].value
      .map { jsValue => jsValue.as[JsString].value }
    val answered = jsObject.value("is_answered").as[JsBoolean].value

    QuestionDto(tags, answered)
  }
}
