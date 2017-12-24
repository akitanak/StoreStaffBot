package com.github.akitanak.storestaffbot.chatif

import com.github.akitanak.storestaffbot.chatif.controller.{LineMessageController, LineMessageControllerImpl}
import com.github.akitanak.storestaffbot.chatif.domain.MessageSender
import com.github.akitanak.storestaffbot.chatif.infrastructure.LineMessageSender
import com.google.inject.AbstractModule

class ChatIfModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[LineMessageController]).to(classOf[LineMessageControllerImpl])
    bind(classOf[MessageSender]).to(classOf[LineMessageSender])
  }
}
