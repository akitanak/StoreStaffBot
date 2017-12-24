package com.github.akitanak.storestaffbot.chatif.infrastructure

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers._
import com.github.akitanak.storestaffbot.chatif.ChatIfActorSystem._
import com.github.akitanak.storestaffbot.chatif.domain.MessageSender
import com.github.akitanak.storestaffbot.chatif.request.line.reply.{ReplyMessage, TextMessage}
import com.github.akitanak.storestaffbot.chatif.util.{ActorLogging, ChatIfConfig, Logging}
import io.circe.syntax._

import scala.concurrent.Future

class LineMessageSender extends MessageSender with Logging {

  private val replyEndpoint = Uri(ChatIfConfig.config.getString("line.reply-endpoint"))
  private val pushEndpoint = Uri(ChatIfConfig.config.getString("line.push-endpoint"))
  private val authToken = ChatIfConfig.config.getString("line.auth-token")
  private val header = List(headers.Authorization(OAuth2BearerToken(authToken)))

  implicit val executionContext = system.dispatcher

  def replyTextMessage(message: String, token: String): Future[Unit] = {
    val replyMessage = ReplyMessage(token, Seq(TextMessage(message)))

    val request = HttpRequest(
      method = HttpMethods.POST,
      uri = replyEndpoint,
      headers = header,
      entity = HttpEntity(ContentTypes.`application/json`, replyMessage.asJson.toString())
    )

    Http().singleRequest(request).map {
      case HttpResponse(StatusCodes.OK, _, _, _) => logger.debug("reply textMessage successfully.")
      case HttpResponse(StatusCodes.BadRequest, _, entity, _) => logger.error(s"reply textMessage failed. response: ${entity.toString}")
    }
  }
  def sendTextMessage(message: String, to: String): Future[Unit] = {
    ???
  }
}
