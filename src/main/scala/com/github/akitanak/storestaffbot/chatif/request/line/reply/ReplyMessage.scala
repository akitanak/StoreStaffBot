package com.github.akitanak.storestaffbot.chatif.request.line.reply

import io.circe._
import io.circe.generic.semiauto._

case class ReplyMessage(replyToken: String, messages: Seq[Message])

object ReplyMessage {
  implicit val encode: Encoder[ReplyMessage] = deriveEncoder[ReplyMessage]
  implicit val decode: Decoder[ReplyMessage] = deriveDecoder[ReplyMessage]
}
