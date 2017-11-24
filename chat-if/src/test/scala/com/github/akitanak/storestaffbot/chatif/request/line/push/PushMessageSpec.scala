package com.github.akitanak.storestaffbot.chatif.request.line.push

import com.github.akitanak.storestaffbot.chatif.request.line.reply.TextMessage
import io.circe._
import io.circe.syntax._
import org.scalatest.{Matchers, WordSpec}

class PushMessageSpec extends WordSpec with Matchers {

  "PushMessage" should {

    val message = PushMessage(
      to = "_to_",
      messages = Seq(
        TextMessage("_text_")
      )
    )

    val json = Json.obj(
      "to" -> Json.fromString("_to_"),
      "messages" -> Json.arr(
        Json.obj(
          "type" -> Json.fromString("text"),
          "text" ->  Json.fromString("_text_")
        )
      )
    )

    "encode to JSON" in {
      message.asJson should be(json)
    }

    "decode from JSON" in {
      json.as[PushMessage] should be(Right(message))
    }
  }
}
