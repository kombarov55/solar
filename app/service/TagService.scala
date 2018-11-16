package service

import javax.inject.Singleton

/**
  * Сервис, выполняющий запрос по получению количества тегов из StackOverflow.
  */
@Singleton
class TagService {

  def countTags(searchTags: Seq[String]): Map[String, Int] = {


    Map("java" -> 1)
  }

  def stub = "{'stub': 'true'}"

}
