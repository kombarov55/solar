package service.da

import com.google.inject.Inject
import play.api.libs.json.JsValue
import play.api.libs.ws.WSClient

import scala.concurrent.Future

class StackOverflowDaService @Inject()(private val wsClient: WSClient) extends StackOverflowDa {

  import scala.concurrent.ExecutionContext.Implicits.global


  override def getByTag(tag: String): Future[JsValue] = {
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
