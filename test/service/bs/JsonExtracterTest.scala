package service.bs

import org.scalatestplus.play.PlaySpec
import service.da.StackOverflowDaStubService
import org.scalatest._
import Matchers._

class JsonExtracterTest extends PlaySpec {

  val service = new StackOverflowDaStubService()
  val jsonExtracter = new JsonExtracter()

  "JsonExtracter" should {
    "count tags correctly" in {
      val jsValue = service.loadJson()

      val m1 = jsonExtracter.countTags(jsValue)
      val m2 = jsonExtracter.countTags(jsValue)

      val result = jsonExtracter.mergeResults(List(m1, m2))

      assert(result("clojure") == 10)
      assert(result("functional-programming") == 2)
      assert(result("ring") == 2)
    }
  }
}
