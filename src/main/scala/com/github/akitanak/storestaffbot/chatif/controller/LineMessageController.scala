package com.github.akitanak.storestaffbot.chatif.controller

import akka.actor.Props
import com.github.akitanak.storestaffbot.chatif.request.line.webhook._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import com.github.akitanak.storestaffbot.chatif.ChatIfActorSystem._
import com.github.akitanak.storestaffbot.chatif.application.TextMessageFacadeActor
import com.github.akitanak.storestaffbot.chatif.util.Logging
import com.google.inject.Singleton

trait LineMessageController {
  def receiveMessage(request: WebhookEvents): Future[Seq[String]]
}

@Singleton
class LineMessageControllerImpl extends LineMessageController with Logging {

  val textMessageReceiver = system.actorOf(Props[TextMessageFacadeActor], "txtMsgRcvr")

  override def receiveMessage(request: WebhookEvents): Future[Seq[String]] = Future {
    request.events.map {
      case message: MessageEvent =>
        handleMessage(message)
        "message received."
      case _ =>
        "other webhook received."
    }
  }.recover {
    case t: Throwable =>
      logger.error("unknown error.", t)
      Seq(s"${t.getMessage}")
  }

  private def handleMessage(message: MessageEvent): Unit = {

    message.message match {
      case text:     TextMessage      => textMessageReceiver ! message
      case image:    ImageMessage     => ???
      case video:    VideoMessage     => ???
      case audio:    AudioMessage     => ???
      case file:     FileMessage      => ???
      case location: LocationMessage  => ???
      case sticker:  StickerMessage   => ???
    }
  }
}
