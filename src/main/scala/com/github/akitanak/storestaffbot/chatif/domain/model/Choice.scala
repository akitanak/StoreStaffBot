package com.github.akitanak.storestaffbot.chatif.domain.model

import java.net.URL

case class Choice(
  imageUrl: Option[URL],
  title: Option[String],
  text: String,
  actions: Seq[Action]
)
