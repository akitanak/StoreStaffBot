package com.github.akitanak.storestaffbot.chatif

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.Materializer
import com.github.akitanak.storestaffbot.chatif.ChatIfActorSystem._
import com.github.akitanak.storestaffbot.chatif.controller.LineMessageController
import com.github.akitanak.storestaffbot.chatif.request.line.webhook.WebhookEvents
import com.github.akitanak.storestaffbot.chatif.util.ChatIfConfig.config
import com.google.inject.Guice
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.io.StdIn

object WebServer {

  val injector = Guice.createInjector(new ChatIfModule)

  val lineMessageController = injector.getInstance(classOf[LineMessageController])

  def main(args: Array[String]): Unit = {

    implicit val executionContext = system.dispatcher

    val port = config.getInt("port")
    val interface = "0.0.0.0"
    val bindingFuture = Http().bindAndHandle(route, interface, port)

    println(s"Server online at http://$interface:$port/")
  }

  def route(implicit materiarizer: Materializer): Route = {
    import Directives._
    import FailFastCirceSupport._
    import io.circe.generic.auto._

    path("status") {
      get {
        complete(HttpEntity(ContentTypes.`application/json`, """{"status":"OK"}"""))
      }
    } ~
    path("message") {
      post {
        println("receive message.")
        entity(as[WebhookEvents]) { request =>
          println(request)
          val response =lineMessageController.receiveMessage(request)
          complete {
            (StatusCodes.OK, response)
          }
        }
      }
    }
  }

}
