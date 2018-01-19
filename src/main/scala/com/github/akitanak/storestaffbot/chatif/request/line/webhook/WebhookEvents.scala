package com.github.akitanak.storestaffbot.chatif.request.line.webhook

import java.nio.charset.StandardCharsets
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

import io.circe._
import io.circe.generic.semiauto._

case class WebhookEvents(events: Seq[Event])

object WebhookEvents {
  implicit val encoder: Encoder[WebhookEvents] = deriveEncoder[WebhookEvents]
  implicit val decoder: Decoder[WebhookEvents] = deriveDecoder[WebhookEvents]

  private val mac = {
    val channelSecret = "hogehoge"
    val secretKeySpec = new SecretKeySpec(channelSecret.getBytes, "HmacSHA256")
    val mac = Mac.getInstance("HmacSHA256")
    mac.init(secretKeySpec)
    mac
  }

  def isValidRequest(header: String, requestBody: String): Boolean = {
    val signature = Base64.getEncoder.encodeToString(mac.doFinal(requestBody.getBytes(StandardCharsets.UTF_8)))
    signature == header
  }
}
