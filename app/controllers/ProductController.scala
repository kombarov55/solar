package controllers

import javax.inject.Inject
import model.Product
import play.api.mvc.{AbstractController, ControllerComponents}

class ProductController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def list() = Action { implicit request =>
    val products = Product.findAll()

    Ok(views.html.list(products))
  }

  def details(ean: Long) = Action {
    val product = Product.findByEan(Product.MAGIC_EAN)

    Ok(views.html.details(product))
  }

//  import play.api.libs.json._
//
//  val data = Json.parse("")
//  val tags = data \\ "tags"
//
//  val requiredTag = "clojure"
//
//  tags.filter { tagElem => tagElem.as[JsArray].value.exists { _.as[String] ==  requiredTag } }
//
//
//
//tags.filter { tagElem =>tagElem.as[JsArray].value.exists { _.as[String] ==  requiredTag } }
////  val sval = tags(0)(0).as[JsString]
////  sval.value == "filter"


}
