package com.github.akitanak.storestaffbot.chatif.request.neato

import io.circe.{Decoder, Encoder, HCursor, Json}

case class Error(message: String, errors: Map[String, String])

object Error {
  implicit val encode: Encoder[Error] = new Encoder[Error] {
    override final def apply(error: Error): Json = Json.obj(
      "message" -> Json.fromString(error.message),
      "errors" -> Json.obj(
        error.errors.map { case (key, value) =>
          key -> Json.fromString(value)
        }.toList: _*
      )
    )
  }

  implicit val decode: Decoder[Error] = new Decoder[Error] {
    override final def apply(c: HCursor): Decoder.Result[Error] =
      for {
        message <- c.downField("message").as[String]
        errors <- c.downField("errors").as[Map[String, String]]
      } yield {
        Error(message, errors)
      }
  }
}
