package com.github.akitanak.storestaffbot.chatif.domain

import com.github.akitanak.storestaffbot.chatif.domain.model.websearch.SearchResult

import scala.concurrent.Future

trait WebSearch {
  def searchWithKeywords(keywords: Seq[String]): Future[Seq[SearchResult]]
}
