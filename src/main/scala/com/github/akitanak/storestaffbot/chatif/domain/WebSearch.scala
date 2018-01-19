package com.github.akitanak.storestaffbot.chatif.domain

import com.github.akitanak.storestaffbot.chatif.domain.model.websearch.SearchResult

trait WebSearch {
  def searchWithKeywords(keywords: Seq[String]): Seq[SearchResult]
}
