package service.bs

import javax.inject.Singleton
import play.api.libs.json.{JsArray, JsString, JsValue}

import scala.collection.mutable

/**
  * Функции для получения данных из json.
  */
@Singleton
class JsonExtracter {

  /**
    * Посчитать количество встречаемых тегов в ответе.
    *
    * @param jsValue ответ.
    * @return map из тега -> количество их в ответе.
    */
  def countTags(jsValue: JsValue): Map[String, Int] = {
    val tags = jsValue \\ "tags"

    tags
      .flatMap { elem => elem.as[JsArray].value }
      .map { jsString => jsString.as[JsString].value }
      .foldLeft(new mutable.HashMap[String, Int]) { (acc, name) =>
        accumulate(acc, name)

        acc
      }.toMap
  }

  /**
    * Слиять всех map-ы, просуммировав все количества тегов.
    *
    * @param maps список map.
    * @return map из имени тега -> суммированное количество тегов из всех map.
    */
  def mergeResults(maps: List[Map[String, Int]]): Map[String, Int] = {
    val resultMap = mutable.Map[String, Int]()

    for (map <- maps) {
      map.foreach { case(name, count) =>
        accumulate(resultMap, name, count)
      }
    }

    resultMap.toMap
  }

  /**
    * Увеличить счётчик внутри map.
    *
    * @param name имя ключа.
    * @param map map.
    */
  private def accumulate(map: mutable.Map[String, Int], name: String, initialValue: Int = 1): Unit = {
    val prevValue = map.put(name, initialValue)
    if (prevValue.isDefined) {
      map.put(name, prevValue.get + initialValue)
    }
  }
}
