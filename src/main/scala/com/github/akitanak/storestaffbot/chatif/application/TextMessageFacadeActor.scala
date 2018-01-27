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

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

case class WebSearchResults(results: Seq[SearchResult])

class TextMessageFacadeActor extends Actor with ActorLogging {

  private val messageSender = injector.getInstance(classOf[MessageSender])
  private val webSearchActor = system.actorOf(Props[WebSearchActor], "webSearch")
  private implicit val executionContext: ExecutionContext = system.dispatcher


  override def receive: Receive = {
    case MessageEvent(token, timestamp, source: SourceUser, message: TextMessage) =>
      logger.info(s"a message received from ${source.userId}. message: [${message.id}] ${message.text}")

      dispatchMessage(message, token)
  }

  private def dispatchMessage(message: TextMessage, token: String): Unit = {
    implicit val timeout: Timeout = Timeout(5.seconds)

    val webSearchRegex = """(.+)(?:について|を)検索.*""".r
    val robotCleanerRegex = """.*掃除.*""".r

    message.text match {
      case webSearchRegex(searchWord) =>
        webSearchActor ? Query(searchWord.split(" ")) map {
          case WebSearchResults(results) =>
            messageSender.replyChoicesMessage(toMessage(results), token)
          case _ =>
            logger.error("cannot match web search results.")
        }
      case robotCleanerRegex =>

      case text =>
        messageSender.replyTextMessage(s"$text\n https://google.com", token)
    }
  }

  private def toMessage(searchResults: Seq[SearchResult]): Seq[Choice] = {
    searchResults.map { result =>
      Choice(
        imageUrl = None,
        title = None,
        text = result.title,
        actions = Seq(
          UriAction("ページを確認", result.url.toURI)
        )
      )
    }.take(10)
  }
}
