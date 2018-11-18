package dto

/**
  * DTO количества.
  *
  * @param count сколько раз тег встретился.
  * @param answered количество отвеченных вопросов с этим тегом.
  */
case class CountDto(
   var count: Int,
   var answered: Int
)
