package com.github.akitanak.storestaffbot.chatif.domain

import akka.actor.Actor
import com.github.akitanak.storestaffbot.chatif.ChatIfActorSystem._
import com.github.akitanak.storestaffbot.chatif.WebServer.injector
import com.github.akitanak.storestaffbot.chatif.application.WebSearchResults
import com.github.akitanak.storestaffbot.chatif.util.ActorLogging

package websearch {
  case class Query(keywords: Seq[String])
}


class WebSearchActor extends Actor with ActorLogging {

  val webSearcher = injector.getInstance(classOf[WebSearch])
  implicit val executionContext = system.dispatcher

  override def receive: Receive = {

    case websearch.Query(keywords) =>
      logger.info(s"web search request received. keywords: $keywords")
      val results = webSearcher.searchWithKeywords(keywords)
      logger.info(s"return search results. size: ${results.size}")
      sender !  WebSearchResults(results)
  }

}
