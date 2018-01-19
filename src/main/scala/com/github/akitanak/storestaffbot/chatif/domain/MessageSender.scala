package com.github.akitanak.storestaffbot.chatif.domain

import com.github.akitanak.storestaffbot.chatif.domain.model.Choice

import scala.concurrent.Future

trait MessageSender {
  def replyTextMessage(message: String, token: String): Future[Unit]
  def replyChoicesMessage(choices: Seq[Choice], token: String): Future[Unit]

  def sendTextMessage(message: String, to: String): Future[Unit]

}
