package com.github.akitanak.storestaffbot.chatif.util

import akka.event.slf4j.Logger

trait Logging {
  val logger = Logger.apply(this.getClass.getName)
}
