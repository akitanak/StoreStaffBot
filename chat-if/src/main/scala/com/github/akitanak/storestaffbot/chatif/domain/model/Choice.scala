package com.github.akitanak.storestaffbot.chatif.domain.model

import java.net.URL
import java.net.URI

case class Choice(
  imageUrl: Option[URL],
  title: Option[String],
  text: String,
  actions: Seq[Action]
)

sealed abstract class Action(label: String)

case class PostbackAction(label: String, data: String, text: String) extends Action(label)

case class MessageAction(label: String, text: String) extends Action(label)

case class UriAction(label: String, Uri: URI) extends Action(label)
