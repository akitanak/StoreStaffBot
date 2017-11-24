package com.github.akitanak.storestaffbot.chatif.request.line.multicast

import com.github.akitanak.storestaffbot.chatif.request.line.reply.TextMessage
import io.circe._
import io.circe.syntax._
import org.scalatest.{Matchers, WordSpec}

class MulticastMessageSpec extends WordSpec with Matchers {

  "PushMessage" should {

    val message = MulticastMessage(
      to = Seq("_to_1_", "_to_2_"),
      messages = Seq(
        TextMessage("_text_")
      )
    )

    val json = Json.obj(
      "to" -> Json.arr(
        Json.fromString("_to_1_"),
        Json.fromString("_to_2_"),
      ),
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
      json.as[MulticastMessage] should be(Right(message))
    }
  }
}
