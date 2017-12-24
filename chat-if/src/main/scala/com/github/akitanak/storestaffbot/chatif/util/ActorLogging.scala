package com.github.akitanak.storestaffbot.chatif.util

import akka.actor.Actor
import akka.event.Logging

trait ActorLogging extends Actor {
  val logger = Logging(context.system, this)
}
