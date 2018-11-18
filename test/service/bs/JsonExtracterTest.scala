package service.bs

import java.io.FileInputStream

import org.scalatestplus.play.PlaySpec
import org.scalatest._
import Matchers._
import play.api.libs.json.{JsValue, Json}

class JsonExtracterTest extends PlaySpec {

  val jsonExtracter = new JsonExtracter()

  "JsonExtracter" should {
    "count tags correctly" in {
      val jsValue = loadJson()

      val m1 = jsonExtracter.countTags(jsValue)
      val m2 = jsonExtracter.countTags(jsValue)

      val result = jsonExtracter.mergeResults(List(m1, m2))

      assert(result("clojure") == 10)
      assert(result("functional-programming") == 2)
      assert(result("ring") == 2)
    }
  }


  private def loadJson(): JsValue = {
    val in = new FileInputStream("/home/nikolay/Documents/repo/solar/[1;solar/public/images/test-data.json")
    val jsValue = Json.parse(in)

    jsValue
  }
}
