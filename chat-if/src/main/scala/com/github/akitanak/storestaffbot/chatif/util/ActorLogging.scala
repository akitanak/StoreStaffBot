package com.github.akitanak.storestaffbot.chatif.util

import akka.actor.Actor

trait ActorLogging extends Actor {
  val logger = akka.event.Logging(context.system, this)
}
