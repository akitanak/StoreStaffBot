package com.github.akitanak.storestaffbot.chatif.infrastructure

import java.net.URI

import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.Await
import scala.concurrent.duration._

class GoogleSearchSpec extends WordSpec with Matchers {

  "GoogleSearch" should {

    val googleSearch = new GoogleSearch

    "return search result" in {

      val keywords = Seq("line", "at")

      val results = Await.result(googleSearch.searchWithKeywords(keywords), 5.seconds)

      results.head.url should be(new URI("http://at.line.me/"))

    }

  }

}
