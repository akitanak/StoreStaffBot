package com.github.akitanak.storestaffbot.chatif.request.line.webhook

import io.circe._
import io.circe.syntax._
import org.scalatest.{Matchers, WordSpec}

class WebhookEventsSpec extends WordSpec with Matchers {

  val messageEvent = MessageEvent(
    replyToken = "_replyToken_",
    timestamp = Long.MaxValue,
    source = SourceUser(
      userId = "_userId_"
    ),
    message = TextMessage(
      id = "_id_",
      text = "_text_"
    )
  )

  val imageEvent = messageEvent.copy(
    message = ImageMessage(id = "_id_")
  )

  val webhookEvents = WebhookEvents(
    Seq(
      messageEvent,
      imageEvent
    )
  )

  val webhookJson = Json.obj(
    "events" -> Json.arr(
      Json.obj(
        "type" -> Json.fromString("message"),
        "replyToken" -> Json.fromString("_replyToken_"),
        "timestamp" -> Json.fromLong(Long.MaxValue),
        "source" -> Json.obj(
          "type" -> Json.fromString("user"),
          "userId" -> Json.fromString("_userId_")
        ),
        "message" -> Json.obj(
          "type" -> Json.fromString("text"),
          "id" -> Json.fromString("_id_"),
          "text" -> Json.fromString("_text_")
        )
      ),
      Json.obj(
        "type" -> Json.fromString("message"),
        "replyToken" -> Json.fromString("_replyToken_"),
        "timestamp" -> Json.fromLong(Long.MaxValue),
        "source" -> Json.obj(
          "type" -> Json.fromString("user"),
          "userId" -> Json.fromString("_userId_")
        ),
        "message" -> Json.obj(
          "type" -> Json.fromString("image"),
          "id" -> Json.fromString("_id_")
        )
      )
    )
  )

  "WebhookEvents" should {
    "encode to JSON" in {
      webhookEvents.asJson should be(webhookJson)
    }

    "decode from JSON" in {
      webhookJson.as[WebhookEvents] should be(Right(webhookEvents))
    }
  }

}
