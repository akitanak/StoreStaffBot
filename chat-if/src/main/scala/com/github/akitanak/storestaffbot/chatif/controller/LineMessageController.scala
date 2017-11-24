package com.github.akitanak.storestaffbot.chatif.controller

import com.github.akitanak.storestaffbot.chatif.request.MessageRequest
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait LineMessageController {
  def receiveMessage(request: MessageRequest): Future[String]
}

class LineMessageControllerImpl extends LineMessageController {
  override def receiveMessage(request: MessageRequest): Future[String] = Future {
    "Good Evening, World!"
  }
}
