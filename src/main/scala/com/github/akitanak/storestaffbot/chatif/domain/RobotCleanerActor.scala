package com.github.akitanak.storestaffbot.chatif.domain

import akka.actor.Actor
import com.github.akitanak.storestaffbot.chatif.ChatIfActorSystem._
import com.github.akitanak.storestaffbot.chatif.WebServer.injector
import com.github.akitanak.storestaffbot.chatif.domain.robotcleaner.{CheckStatus, UserAuthorized}
import com.github.akitanak.storestaffbot.chatif.util.ActorLogging

import scala.collection.mutable
import scala.concurrent.duration._

package robotcleaner {

  import java.net.URL

  import com.typesafe.config.ConfigFactory

  case class CheckStatus(userId: String)
  case class UserAuthorized(userId: String, code: String)
  case class RequireToken(userId: String) {
    import RequireToken._
    def pleaseAuthThisApp: (String, URL) = {
      (
        "このアプリを認証してください。",
        new URL(s"https://apps.neatorobotics.com/oauth2/authorize?response_type=code&client_id=$clientId&redirect_uri=$redirectUrl&scope=$requestedScope&state=$userId")
      )
    }
  }

  object RequireToken {
    val conf = ConfigFactory.load.getConfig("neato")
    val clientId = conf.getString("client-id")
    val redirectUrl = conf.getString("redirect-url")
    val requestedScope = conf.getString("requested-scope")
  }
}


class RobotCleanerActor extends Actor with ActorLogging {
  import akka.actor.OneForOneStrategy
  import akka.actor.SupervisorStrategy._

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 20 seconds) {
      case _: Exception => Restart
    }

  implicit val executionContext = system.dispatcher

  private val tokens: mutable.Map[String, String] = mutable.Map[String, String]()


  override def receive: Receive = {

    case CheckStatus(userId) =>
      logger.debug(s"check status. user: $userId")

      tokens.get(userId) match {
        case Some(token) =>
          logger.debug(s"user authorized. user: $userId, token: $token")

        case None =>
          logger.debug(s"user not authorized. user: $userId")

      }

    case UserAuthorized(userId, code) =>
      logger.debug(s"user authorizes this application. userId: $userId")

  }




}
