package service.da

import java.io.FileInputStream

import play.api.libs.json.{JsValue, _}

import scala.concurrent.Future
import scala.io.Source

class StackOverflowDaStubService extends StackOverflowDa {

  import scala.concurrent.ExecutionContext.Implicits.global

  /**
    * Получить список вопросов по тегу.
    *
    * @param tag теги, по которым выполнить поиск.
    * @return Future json-ответа.
    */
  override def getByTag(tag: String): Future[JsValue] = Future { loadJson() }

  def loadJson(): JsValue = {
    Source.fromFile("/home/nikolay/Documents/repo/solar/[1;solar/public/images/test-data.json")
    val jsValue = Json.parse(new FileInputStream("/home/nikolay/Documents/repo/solar/[1;solar/public/images/test-data.json"))

    jsValue
  }

}
