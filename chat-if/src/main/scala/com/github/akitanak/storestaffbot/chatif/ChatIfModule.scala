package com.github.akitanak.storestaffbot.chatif

import com.github.akitanak.storestaffbot.chatif.controller.{LineMessageController, LineMessageControllerImpl}
import com.github.akitanak.storestaffbot.chatif.domain.{MessageSender, WebSearch}
import com.github.akitanak.storestaffbot.chatif.infrastructure.{GoogleSearch, LineMessageSender}
import com.google.inject.AbstractModule

class ChatIfModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[LineMessageController]).to(classOf[LineMessageControllerImpl])
    bind(classOf[MessageSender]).to(classOf[LineMessageSender])
    bind(classOf[WebSearch]).to(classOf[GoogleSearch])
  }
}
