package com.github.akitanak.storestaffbot.chatif.application

import akka.actor.{Actor, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.github.akitanak.storestaffbot.chatif.ChatIfActorSystem._
import com.github.akitanak.storestaffbot.chatif.WebServer.injector
import com.github.akitanak.storestaffbot.chatif.domain.model.websearch.SearchResult
import com.github.akitanak.storestaffbot.chatif.domain.model.{Choice, Confirm, MessageAction, UriAction}
import com.github.akitanak.storestaffbot.chatif.domain.robotcleaner.{CheckStatus, RequireToken}
import com.github.akitanak.storestaffbot.chatif.domain.websearch.Query
import com.github.akitanak.storestaffbot.chatif.domain.{MessageSender, RobotCleanerActor, WebSearchActor}
import com.github.akitanak.storestaffbot.chatif.request.line.webhook.{MessageEvent, SourceUser, TextMessage}
import com.github.akitanak.storestaffbot.chatif.util.ActorLogging

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

case class WebSearchResults(results: Seq[SearchResult])

class TextMessageFacadeActor extends Actor with ActorLogging {
  import akka.actor.OneForOneStrategy
  import akka.actor.SupervisorStrategy._

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 20 seconds) {
      case _: Exception => Restart
    }

  private val messageSender = injector.getInstance(classOf[MessageSender])
  private val webSearchActor = system.actorOf(Props[WebSearchActor], "webSearch")
  private val robotCleanerActor = system.actorOf(Props[RobotCleanerActor], "robotCleaner")
  private implicit val executionContext: ExecutionContext = system.dispatcher

  private val webSearchRegex = """(.+)(?:について|を)検索.*""".r
  private val robotCleanerRegex = """.*掃除.*""".r

  override def receive: Receive = {
    case MessageEvent(token, timestamp, source: SourceUser, message: TextMessage) =>
      logger.info(s"a message received from ${source.userId}. message: [${message.id}] ${message.text}")

      dispatchMessage(message, token, source.userId)

  }

  private def dispatchMessage(message: TextMessage, token: String, userId: String): Unit = {
    implicit val timeout: Timeout = Timeout(5.seconds)
    logger.info("dispatch message.")
    message.text match {
      case webSearchRegex(searchWord) =>
        logger.info(s"websearch: $searchWord")
        webSearchActor ? Query(searchWord.split(" ")) map {
          case WebSearchResults(results) =>
            messageSender.replyChoicesMessage(toMessage(results), token)
          case _ =>
            logger.error("cannot match web search results.")
        }

      case robotCleanerRegex() =>
        logger.info("robot cleaner")
        robotCleanerActor ? CheckStatus(userId) map {
          case requireToken: RequireToken =>
            messageSender.replyConfirmMessage(toMessage(requireToken), token)
        }

      case text =>
        logger.info(s"text: $text")
        messageSender.replyTextMessage(text, token)
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

  private def toMessage(requireToken: RequireToken): Confirm = {
    val (message, url) = requireToken.pleaseAuthThisApp
    Confirm(
      message,
      actions = Seq(
        UriAction("はい", url.toURI),
        MessageAction("いいえ", "いいえ")
      )
    )
  }
}
