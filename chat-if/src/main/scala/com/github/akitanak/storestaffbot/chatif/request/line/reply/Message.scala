package com.github.akitanak.storestaffbot.chatif.request.line.reply

import io.circe.Decoder.Result
import io.circe._
import io.circe.syntax._

sealed abstract class Message(val `type`: String)

object Message {
  implicit val encode: Encoder[Message] = new Encoder[Message] {
    override def apply(message: Message): Json =
      message match {
        case text: TextMessage => text.asJson
        case template: TemplateMessage => template.asJson
      }
  }

  implicit val decode: Decoder[Message] = new Decoder[Message] {
    override def apply(c: HCursor): Result[Message] =
      c.downField("type").as[String] flatMap {
        case "text" => c.as[TextMessage]
        case "template" => c.as[TemplateMessage]
      }
  }
}


case class TextMessage(text: String) extends Message("text")

object TextMessage {
  implicit val encode: Encoder[TextMessage] = new Encoder[TextMessage] {
    override def apply(message: TextMessage): Json = Json.obj(
      "type" -> Json.fromString(message.`type`),
      "text" -> Json.fromString(message.text)
    )
  }

  implicit val decode: Decoder[TextMessage] = new Decoder[TextMessage] {
    override def apply(c: HCursor): Result[TextMessage] =
      for {
        text <- c.downField("text").as[String]
      } yield {
        TextMessage(text)
      }
  }
}

case class TemplateMessage(altText: String, template: Template) extends Message("template")

object TemplateMessage {
  implicit val encode: Encoder[TemplateMessage] = new Encoder[TemplateMessage] {
    override final def apply(message: TemplateMessage): Json = Json.obj(
      "type" -> Json.fromString(message.`type`),
      "altText" -> Json.fromString(message.altText),
      "template" -> message.template.asJson
    )
  }

  implicit val decode: Decoder[TemplateMessage] = new Decoder[TemplateMessage] {
    override def apply(c: HCursor): Result[TemplateMessage] =
      for {
        altText <- c.downField("altText").as[String]
        template <- c.downField("template").as[Template]
      } yield {
        TemplateMessage(altText, template)
      }
  }
}

