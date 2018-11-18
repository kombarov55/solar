package service.bs

import dto.CountDto
import javax.inject.{Inject, Singleton}
import service.da.StackOverflowDaService
import util.FixedSizeExecutionContext

import scala.concurrent.Future

/**
  * Сервис для подсчёта количества встречаемых тегов в вопросах, найденных по тегам.
  *
  * @param daService сервис по получению данных по вопросам stackOverflow.
  */
@Singleton
class TagService @Inject()(
    private val daService: StackOverflowDaService,
    private val jsonExtracter: JsonExtracter,
    implicit private val executionContext: FixedSizeExecutionContext
) {

  /**
    * Получить количество встречаемых тегов в вопросах, которые нашли поиском по тегу.
    *
    * @param searchTags теги, по которым необходимо выполнить поиск.
    * @return map из имени тега -> количества.
    */
  def countTags(searchTags: Seq[String]): Future[Map[String, CountDto]] = {
    var futureList: List[Future[Map[String, CountDto]]] = Nil

    for (tag <- searchTags) {
      val future = daService.getByTag(tag)
        .map { jsValue =>
          jsonExtracter.countTags(jsValue)
        }

      futureList = future :: futureList
    }

    Future.sequence(futureList).map { resultList =>
      jsonExtracter.mergeResults(resultList)
    }
  }
}

