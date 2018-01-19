package com.github.akitanak.storestaffbot.chatif

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object ChatIfActorSystem {
  implicit val system = ActorSystem("ChatIf")
  implicit val materializer = ActorMaterializer()

  Runtime.getRuntime.addShutdownHook(new Thread(
    () => {
      println("shutdown ActorSystem...")
      system.terminate()
    }
  ))
}
