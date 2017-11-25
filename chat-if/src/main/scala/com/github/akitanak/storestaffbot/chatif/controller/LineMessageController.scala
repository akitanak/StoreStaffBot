package com.github.akitanak.storestaffbot.chatif.controller

import com.github.akitanak.storestaffbot.chatif.request.line.webhook.{TextMessage, MessageEvent, WebhookEvents}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait LineMessageController {
  def receiveMessage(request: WebhookEvents): Future[Seq[String]]
}

class LineMessageControllerImpl extends LineMessageController {
  override def receiveMessage(request: WebhookEvents): Future[Seq[String]] = Future {
    request.events.map {
      case message: MessageEvent =>
        println(message.message.asInstanceOf[TextMessage])
        "message received."
      case _ =>
        "other webhook received."
    }
  }
}
