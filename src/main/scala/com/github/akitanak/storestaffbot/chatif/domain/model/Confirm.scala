package com.github.akitanak.storestaffbot.chatif.domain.model

case class Confirm(
  text: String,
  actions: Seq[Action]
)
