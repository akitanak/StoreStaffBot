package com.github.akitanak.storestaffbot.chatif.infrastructure

import java.net.{URI, URL}

import com.github.akitanak.storestaffbot.chatif.ChatIfActorSystem._
import com.github.akitanak.storestaffbot.chatif.domain.WebSearch
import com.github.akitanak.storestaffbot.chatif.domain.model.websearch.SearchResult
import com.github.akitanak.storestaffbot.chatif.util.Logging
import net.ruippeixotog.scalascraper.browser.JsoupBrowser

import scala.concurrent.ExecutionContext
import scala.util.Try

class GoogleSearch extends WebSearch with Logging {

  private implicit val executionContext: ExecutionContext = system.dispatcher

  override def searchWithKeywords(keywords: Seq[String]): Seq[SearchResult] = {
    val doc = JsoupBrowser().get(s"https://www.google.co.jp/search?q=${keywords.mkString("+")}")
    val body = doc.body
    val elements = body.select("#res .g .r > a")

    elements.map { element =>
      val url = element.attr("href")
      val title = element.text
      extractUrl(url).flatMap(uri => Try(new URL(uri)).toOption).map(SearchResult(title, _))
    }.toSeq.flatten
  }

  private def extractUrl(url: String): Option[String] = {
    val queries = new URI(url).getQuery.split("&")
    // FIXME
    queries.find(_.startsWith("q=")).map(_.substring(2))
  }

}
