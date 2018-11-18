package service.da

import com.google.inject.Inject
import javax.inject.Singleton
import play.api.libs.json.JsValue
import play.api.libs.ws.WSClient
import util.FixedSizeExecutionContext

import scala.concurrent.Future

/**
  * Сервис получения данных из stackOverflow.
  * @param wsClient play.api.libs.ws.WSClient
  * @param fixedSizeExecutionContext ExecutionContext.
  */
@Singleton
class StackOverflowDaService @Inject()(
    private val wsClient: WSClient,
    implicit private val fixedSizeExecutionContext: FixedSizeExecutionContext
) {

  /**
    * Сделать запрос к StackOverflow по переданному тегу.
    * @param tag тег, по которому делать запрос.
    * @return future json ответа.
    */
  def getByTag(tag: String): Future[JsValue] = {
    val URL = "https://api.stackexchange.com/2.2/search"

    val rq = wsClient.url(URL)
      .withQueryStringParameters(
        "pagesize" -> "100",
        "order" -> "desc",
        "sort" -> "creation",
        "site" -> "stackoverflow",
        "tagged" -> tag
      )

    rq.get().map { rs => rs.json }
  }

}
