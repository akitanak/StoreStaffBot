package com.github.akitanak.storestaffbot.chatif

import akka.actor.Props
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.Materializer
import com.github.akitanak.storestaffbot.chatif.ChatIfActorSystem._
import com.github.akitanak.storestaffbot.chatif.controller.LineMessageController
import com.github.akitanak.storestaffbot.chatif.domain.RobotCleanerActor
import com.github.akitanak.storestaffbot.chatif.domain.robotcleaner.UserAuthorized
import com.github.akitanak.storestaffbot.chatif.request.line.webhook.WebhookEvents
import com.github.akitanak.storestaffbot.chatif.util.ChatIfConfig.config
import com.github.akitanak.storestaffbot.chatif.util.Logging
import com.google.inject.Guice
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.io.StdIn

object WebServer extends Logging {

  val injector = Guice.createInjector(new ChatIfModule)

  val lineMessageController = injector.getInstance(classOf[LineMessageController])
  private lazy val robotCleanerActor = system.actorSelection("user/robotCleaner")

  def main(args: Array[String]): Unit = {

    implicit val executionContext = system.dispatcher

    val port = config.getInt("port")
    val interface = "0.0.0.0"
    val bindingFuture = Http().bindAndHandle(route, interface, port)

    logger.info(s"Server online at http://$interface:$port/")
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
        logger.debug("receive message.")
        entity(as[WebhookEvents]) { request =>
          val response =lineMessageController.receiveMessage(request)
          complete {
            (StatusCodes.OK, response)
          }
        }
      }
    } ~
    path("token") {
      get {
        parameter("code".as[String], "state".as[String]) { (code, state) =>
          logger.debug(s"code: $code, state(userId): $state")
          robotCleanerActor ! UserAuthorized(state, code)
          complete {
            StatusCodes.OK
          }
        }
      }
    }
  }

}
