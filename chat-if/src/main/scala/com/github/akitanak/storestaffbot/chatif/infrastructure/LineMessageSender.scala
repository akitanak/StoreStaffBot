package com.github.akitanak.storestaffbot.chatif.infrastructure

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers._
import com.github.akitanak.storestaffbot.chatif.ChatIfActorSystem._
import com.github.akitanak.storestaffbot.chatif.domain.MessageSender
import com.github.akitanak.storestaffbot.chatif.domain.model._
import com.github.akitanak.storestaffbot.chatif.request.line.reply.{CarouselColumn, CarouselTemplate, ReplyMessage, TemplateMessage, TextMessage, MessageAction => LineMsgAction, PostbackAction => LinePBAction, UriAction => LineUriAction}
import com.github.akitanak.storestaffbot.chatif.util.{ChatIfConfig, Logging}
import io.circe.syntax._

import scala.concurrent.Future
import scala.concurrent.duration._

class LineMessageSender extends MessageSender with Logging {

  private val replyEndpoint = Uri(ChatIfConfig.config.getString("line.reply-endpoint"))
  private val pushEndpoint = Uri(ChatIfConfig.config.getString("line.push-endpoint"))
  private val authToken = ChatIfConfig.config.getString("line.auth-token")
  private val header = List(headers.Authorization(OAuth2BearerToken(authToken)))

  private implicit val executionContext = system.dispatcher

  def replyTextMessage(message: String, token: String): Future[Unit] = {
    val reply = ReplyMessage(token, Seq(TextMessage(message)))

    replyMessage(reply)
  }

  def replyChoicesMessage(choices: Seq[Choice], token: String): Future[Unit] = {
    val templateMessage = TemplateMessage(
      altText = "選択肢が表示されます",
      template = CarouselTemplate(
        columns = choices.map { c => CarouselColumn(
          thumbnailImageUrl = c.imageUrl.map(_.toString),
          imageBackgroundColor = None,
          title = c.title,
          text = c.text,
          actions = c.actions.map {
            case PostbackAction(label, data, text) =>
              LinePBAction(label, data, text)
            case MessageAction(label, text) =>
              LineMsgAction(label, text)
            case UriAction(label, uri) =>
              LineUriAction(label, uri.toString)
          }
        )},
        imageAspectRatio = None,
        imageSize = None
      )
    )

    replyMessage(ReplyMessage(token, Seq(templateMessage)))
  }

  private def replyMessage(message: ReplyMessage): Future[Unit] = {
    val request = HttpRequest(
      method = HttpMethods.POST,
      uri = replyEndpoint,
      headers = header,
      entity = HttpEntity(ContentTypes.`application/json`, message.asJson.toString())
    )

    Http().singleRequest(request).map {
      case HttpResponse(StatusCodes.OK, _, _, _) => logger.debug("reply textMessage successfully.")
      case HttpResponse(StatusCodes.BadRequest, _, entity, _) =>
        val body = entity.toStrict(5.seconds).map { e =>
          logger.error(s"reply textMessage failed. response: ${e.data.utf8String}")
        }
    }
  }

  def sendTextMessage(message: String, to: String): Future[Unit] = {
    ???
  }
}
