package service.bs

import javax.inject.{Inject, Singleton}
import service.da.StackOverflowDaService

import scala.concurrent.Future

/**
  * Сервис для подсчёта количества встречаемых тегов в вопросах, найденных по тегам.
  *
  * @param daService сервис по получению данных по вопросам stackOverflow.
  */
@Singleton
class TagService @Inject()(private val daService: StackOverflowDaService, private val jsonExtracter: JsonExtracter) {

  import scala.concurrent.ExecutionContext.Implicits.global

  /**
    * Получить количество встречаемых тегов в вопросах, которые нашли поиском по тегу.
    *
    * @param searchTags теги, по которым необходимо выполнить поиск.
    * @return map из имени тега -> количества.
    */
  def countTags(searchTags: Seq[String]): Future[Map[String, Int]] = {
    //FIXME: пока что только один тег. Далее композировать все теги.
    val tag = searchTags.head

    daService.getByTag(tag)
      .map { jsValue => jsonExtracter.countTags(jsValue) }
  }
}
