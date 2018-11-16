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
}
