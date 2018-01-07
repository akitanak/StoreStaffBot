package com.github.akitanak.storestaffbot.chatif.infrastructure

import java.net.URI

import com.github.akitanak.storestaffbot.chatif.ChatIfActorSystem._
import com.github.akitanak.storestaffbot.chatif.domain.WebSearch
import com.github.akitanak.storestaffbot.chatif.domain.model.websearch.SearchResult
import com.github.akitanak.storestaffbot.chatif.util.Logging
import net.ruippeixotog.scalascraper.browser.JsoupBrowser

class GoogleSearch extends WebSearch with Logging {

  private implicit val executionContext = system.dispatcher

  override def searchWithKeywords(keywords: Seq[String]): Seq[SearchResult] = {
    val doc = JsoupBrowser().get(s"https://www.google.co.jp/search?q=${keywords.mkString("+")}")
    val body = doc.body
    val elements = body.select("#res .g .r > a")

    elements.map { element =>
      val url = element.attr("href")
      val title = element.text
      SearchResult(title, new URI(extractUrl(url)))
    }.toSeq
  }

  private def extractUrl(url: String): String = {
    val queries = new URI(url).getQuery.split("&")
    // FIXME
    queries.find(_.startsWith("q=")).map(_.substring(2)).getOrElse("")
  }

}
