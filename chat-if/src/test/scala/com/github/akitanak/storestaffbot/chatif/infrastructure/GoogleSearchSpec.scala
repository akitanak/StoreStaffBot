package com.github.akitanak.storestaffbot.chatif.infrastructure

import java.net.URI

import org.scalatest.{Matchers, WordSpec}

class GoogleSearchSpec extends WordSpec with Matchers {

  "GoogleSearch" should {

    val googleSearch = new GoogleSearch

    "return search result" in {

      val keywords = Seq("line", "at")

      val results = googleSearch.searchWithKeywords(keywords)

      results.head.url should be(new URI("http://at.line.me/"))

    }

  }

}
