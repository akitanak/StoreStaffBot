package com.github.akitanak.storestaffbot.chatif.domain

import akka.actor.Actor
import com.github.akitanak.storestaffbot.chatif.ChatIfActorSystem._
import com.github.akitanak.storestaffbot.chatif.WebServer.injector
import com.github.akitanak.storestaffbot.chatif.domain.robotcleaner.{CheckStatus, UserAuthorized}
import com.github.akitanak.storestaffbot.chatif.util.ActorLogging

import scala.collection.mutable

package robotcleaner {
  case class CheckStatus(userId: String)
  case class UserAuthorized(userId: String, code: String)
}


class RobotCleanerActor extends Actor with ActorLogging {

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
