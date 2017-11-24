package com.github.akitanak.storestaffbot.chatif

import com.github.akitanak.storestaffbot.chatif.controller.{LineMessageController, LineMessageControllerImpl}
import com.google.inject.AbstractModule

class ChatIfModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[LineMessageController]).to(classOf[LineMessageControllerImpl])
  }
}
