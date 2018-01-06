package com.github.akitanak.storestaffbot.chatif.domain

import akka.actor.Actor
import com.github.akitanak.storestaffbot.chatif.WebServer.injector
import com.github.akitanak.storestaffbot.chatif.domain.model.websearch.Query
import com.github.akitanak.storestaffbot.chatif.util.ActorLogging

class WebSearchService extends Actor with ActorLogging {

  val webSearcher = injector.getInstance(classOf[WebSearch])

  override def receive: Receive = {

    case Query(keywords) =>
      logger.info(s"web search request received. keywords: $keywords")
      sender() ! webSearcher.searchWithKeywords(keywords)

  }

}
