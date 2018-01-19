package com.github.akitanak.storestaffbot.chatif.request.line.push

import com.github.akitanak.storestaffbot.chatif.request.line.reply.Message
import io.circe._
import io.circe.generic.semiauto._

case class PushMessage(to: String, messages: Seq[Message])

object PushMessage {
  implicit val encode: Encoder[PushMessage] = deriveEncoder[PushMessage]
  implicit val decode: Decoder[PushMessage] = deriveDecoder[PushMessage]
}
