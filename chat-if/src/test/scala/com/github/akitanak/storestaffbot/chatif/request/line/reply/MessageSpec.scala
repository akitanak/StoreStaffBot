package com.github.akitanak.storestaffbot.chatif.request.line.reply

import io.circe._
import io.circe.syntax._
import org.scalatest.{Matchers, WordSpec}

class MessageSpec extends WordSpec with Matchers {

  "TextMessage" should {

    val message = TextMessage("_text_")
    val json = Json.obj(
      "type" -> Json.fromString("text"),
      "text" ->  Json.fromString("_text_")
    )

    "encode to JSON" in {
      message.asJson should be(json)
    }

    "decode to JSON" in {
      json.as[TextMessage] should be(Right(message))
    }
  }

  "TemplateMessage" should {
    val actions = Seq(
      PostbackAction("_label_", "_data_", "_text_"),
      MessageAction("_label_", "_text_"),
      UriAction("_label_", "_uri_")
    )

    val actionsJson = Json.arr(
      Json.obj(
        "type" -> Json.fromString("postback"),
        "label" -> Json.fromString("_label_"),
        "data" -> Json.fromString("_data_"),
        "text" -> Json.fromString("_text_")
      ),
      Json.obj(
        "type" -> Json.fromString("message"),
        "label" -> Json.fromString("_label_"),
        "text" -> Json.fromString("_text_")
      ),
      Json.obj(
        "type" -> Json.fromString("uri"),
        "label" -> Json.fromString("_label_"),
        "uri" -> Json.fromString("_uri_")
      )
    )

    val message = TemplateMessage("_altText_", ConfirmTemplate(
      text = "_text_",
      actions = actions
    ))

    val json = Json.obj(
      "type" -> Json.fromString("template"),
      "altText" ->  Json.fromString("_altText_"),
      "template" -> Json.obj(
        "type" -> Json.fromString("confirm"),
        "text" -> Json.fromString("_text_"),
        "actions" -> actionsJson
      )
    )

    "encode to JSON" in {
      message.asJson should be(json)
    }

    "decode to JSON" in {
      json.as[TemplateMessage] should be(Right(message))
    }
  }

  "Message" should {

    val message = TextMessage("_text_").asInstanceOf[Message]
    val json = Json.obj(
      "type" -> Json.fromString("text"),
      "text" ->  Json.fromString("_text_")
    )

    "encode to JSON" in {
      message.asJson should be(json)
    }

    "decode to JSON" in {
      json.as[Message] should be(Right(message))
    }
  }

}
