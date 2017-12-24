package com.github.akitanak.storestaffbot.chatif.application

import akka.actor.Actor
import com.github.akitanak.storestaffbot.chatif.WebServer.injector
import com.github.akitanak.storestaffbot.chatif.domain.MessageSender
import com.github.akitanak.storestaffbot.chatif.request.line.webhook.{MessageEvent, SourceUser, TextMessage}
import com.github.akitanak.storestaffbot.chatif.util.ActorLogging

class TextMessageReceiver extends Actor with ActorLogging {

  val messageSender = injector.getInstance(classOf[MessageSender])

  override def receive: Receive = {
    case MessageEvent(token, timestamp, source: SourceUser, message: TextMessage) =>
      logger.info(s"a message received from ${source.userId}. message: [${message.id}] ${message.text}")
      messageSender.replyTextMessage(message.text, token)
  }
}
