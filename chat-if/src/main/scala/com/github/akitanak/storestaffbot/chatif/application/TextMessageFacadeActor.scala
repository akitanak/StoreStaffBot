package com.github.akitanak.storestaffbot.chatif.application

import akka.actor.{Actor, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.github.akitanak.storestaffbot.chatif.ChatIfActorSystem._
import com.github.akitanak.storestaffbot.chatif.WebServer.injector
import com.github.akitanak.storestaffbot.chatif.domain.model.websearch.SearchResult
import com.github.akitanak.storestaffbot.chatif.domain.model.{Choice, UriAction}
import com.github.akitanak.storestaffbot.chatif.domain.websearch.Query
import com.github.akitanak.storestaffbot.chatif.domain.{MessageSender, WebSearchActor}
import com.github.akitanak.storestaffbot.chatif.request.line.webhook.{MessageEvent, SourceUser, TextMessage}
import com.github.akitanak.storestaffbot.chatif.util.ActorLogging

import scala.concurrent.duration._

case class WebSearchResults(results: Seq[SearchResult])

class TextMessageFacadeActor extends Actor with ActorLogging {

  val messageSender = injector.getInstance(classOf[MessageSender])
  val webSearchActor = system.actorOf(Props[WebSearchActor], "webSearch")
  implicit val executionContext = system.dispatcher


  override def receive: Receive = {
    case MessageEvent(token, timestamp, source: SourceUser, message: TextMessage) =>
      logger.info(s"a message received from ${source.userId}. message: [${message.id}] ${message.text}")

      classifyMessage(message, token)
  }

  private def classifyMessage(message: TextMessage, token: String): Unit = {

    val webSearchRegex = ".*検索.*".r
    implicit val timeout = Timeout(5.seconds)

    message.text match {
      case webSearchRegex() =>
        webSearchActor ? Query(Seq(message.text)) map {
          case WebSearchResults(results) =>
            messageSender.replyChoicesMessage(toMessage(results), token)
          case _ =>
            logger.error("cannot match web search results.")
        }
    }
  }

  private def toMessage(searchResults: Seq[SearchResult]): Seq[Choice] = {
    searchResults.map { result =>
      Choice(
        imageUrl = None,
        title = None,
        text = result.title,
        actions = Seq(
          UriAction("リンクを確認", result.url)
        )
      )
    }
  }
}
