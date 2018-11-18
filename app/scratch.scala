import play.api.libs.json.JsValue

object scratch {

  def getTagsFromUrl(url: String = "/search?tag=clojure&tag=java"): Seq[String] = {
    return url
      .substring(url.indexOf("?") + 1)
      .split("&")
      .map { s =>
        val signIndex = s.indexOf("=")
        val value = s.substring(signIndex + 1)

        value
      }
  }

  object Futures {

    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.Future


    val sum = Future {
      Thread.sleep(1000 * 5)
      (1 to 100000).sum
    }.map { i =>
      println("mapping...")
      Thread.sleep(1000 * 2)
      println("...mapping success")
      i * i
    }




  }

}
