package com.github.akitanak.storestaffbot.chatif.request.line.webhook

import io.circe._
import io.circe.generic.semiauto._

case class Postback(data: String, params: PostbackParams)

object Postback {
  implicit val encoder: Encoder[Postback] = deriveEncoder[Postback]
  implicit val decoder: Decoder[Postback] = deriveDecoder[Postback]
}

case class PostbackParams(date: String, time: String, datetime: String)

object PostbackParams {
  implicit val encoder: Encoder[PostbackParams] = deriveEncoder[PostbackParams]
  implicit val decoder: Decoder[PostbackParams] = deriveDecoder[PostbackParams]
}