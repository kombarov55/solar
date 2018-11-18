package service.da

import com.google.inject.Inject
import javax.inject.Singleton
import play.api.libs.json.JsValue
import play.api.libs.ws.WSClient

import scala.concurrent.Future

@Singleton
class StackOverflowDaService @Inject()(private val wsClient: WSClient) {

  import scala.concurrent.ExecutionContext.Implicits.global

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
