package dto

/**
  * ДТО вопроса.
  *
  * @param tags теги вопроса.
  * @param answered есть ли ответ на вопрос.
  */
case class QuestionDto(
    tags: Seq[String],
    answered: Boolean
)
