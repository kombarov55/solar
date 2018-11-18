package service.da

import play.api.libs.json.JsValue

import scala.concurrent.Future

/**
  * Получение данных из StackOverflow.
  */
trait StackOverflowDa {

  /**
    * Получить список вопросов по тегу.
    *
    * @param tag теги, по которым выполнить поиск.
    * @return Future json-ответа.
    */
  def getByTag(tag: String): Future[JsValue]

}
