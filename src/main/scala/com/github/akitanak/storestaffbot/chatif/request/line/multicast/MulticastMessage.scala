package com.github.akitanak.storestaffbot.chatif.request.line.multicast

import com.github.akitanak.storestaffbot.chatif.request.line.reply.Message
import io.circe._
import io.circe.generic.semiauto._

case class MulticastMessage(to: Seq[String], messages: Seq[Message])

object MulticastMessage {
  implicit val encode: Encoder[MulticastMessage] = deriveEncoder[MulticastMessage]
  implicit val decode: Decoder[MulticastMessage] = deriveDecoder[MulticastMessage]
}
