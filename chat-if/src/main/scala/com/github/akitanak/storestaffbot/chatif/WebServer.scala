package com.github.akitanak.storestaffbot.chatif

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.{ActorMaterializer, Materializer}
import com.github.akitanak.storestaffbot.chatif.controller.LineMessageController
import com.github.akitanak.storestaffbot.chatif.request.line.webhook.WebhookEvents
import com.google.inject.Guice
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.io.StdIn

object WebServer {

  val injector = Guice.createInjector(new ChatIfModule)

  val lineMessageController = injector.getInstance(classOf[LineMessageController])

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("main-system")
    implicit val materializer = ActorMaterializer()

    implicit val executionContext = system.dispatcher

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
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
        entity(as[WebhookEvents]) { request =>
          val response =lineMessageController.receiveMessage(request)
          complete {
            (StatusCodes.OK, response)
          }
        }
      }
    }
  }

}
