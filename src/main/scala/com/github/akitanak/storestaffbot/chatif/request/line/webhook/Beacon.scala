package com.github.akitanak.storestaffbot.chatif.request.line.webhook

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class Beacon(hwid: String, `type`: String, dm: Option[String])

object Beacon {
  implicit val Encoder: Encoder[Beacon] = deriveEncoder[Beacon]
  implicit val Decoder: Decoder[Beacon] = deriveDecoder[Beacon]
}

