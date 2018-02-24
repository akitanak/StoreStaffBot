package com.github.akitanak.storestaffbot.chatif.request.neato.auth

import io.circe._
import io.circe.syntax._
import org.scalatest.{Matchers, WordSpec}

class Oauth2TokenSpec extends WordSpec with Matchers {

  implicit def str2Json(str: String): Json = Json.fromString(str)
  implicit def int2Json(num: Int): Json = Json.fromInt(num)

  "Oauth2TokenRequest" should {


    val request = Oauth2TokenRequest(
      grantType = "authorization_code",
      clientId = "8c3d106944889a848e6f7962b38b022cabfbbe5c2fc8fca24fa4ebb96b1fd92f",
      clientSecret = "f54bec5197e254fa72f12e6ca838a51a18203750d66fe61fbc615fe3c67900ed",
      redirectUri = "https://myapp.example.com/callback",
      code = "9974a63bd9fc5328b09409934204c5e2ae3ab0906d0ad86f9f9eb4e8ba22ef33"
    )

    val requestJson = Json.obj(
      "grant_type" -> "authorization_code",
      "client_id" -> "8c3d106944889a848e6f7962b38b022cabfbbe5c2fc8fca24fa4ebb96b1fd92f",
      "client_secret" -> "f54bec5197e254fa72f12e6ca838a51a18203750d66fe61fbc615fe3c67900ed",
      "redirect_uri" -> "https://myapp.example.com/callback",
      "code" -> "9974a63bd9fc5328b09409934204c5e2ae3ab0906d0ad86f9f9eb4e8ba22ef33"
    )

    "endode request object to Json" in {
      request.asJson should be(requestJson)
    }

    "decode request Json to object" in {
      requestJson.as[Oauth2TokenRequest] should be(Right(request))
    }
  }

  "Oauth2TokenResponse" should {

    val responseJson = Json.obj(
      "access_token" -> "dbaf9757982a9e738f05d249b7b5b4a266b3a139049317c4909f2f263572c781",
      "token_type" -> "bearer",
      "expires_in" -> 1209600,
      "refresh_token" -> "76ba4c5c75c96f6087f58a4de10be6c00b29ea1ddc3b2022ee2016d1363e3a7c",
      "scope" -> "email control_robots"
    )

    val response = Oauth2TokenResponse(
      accessToken = "dbaf9757982a9e738f05d249b7b5b4a266b3a139049317c4909f2f263572c781",
      tokenType = "bearer",
      expiresIn = 1209600,
      refreshToken = "76ba4c5c75c96f6087f58a4de10be6c00b29ea1ddc3b2022ee2016d1363e3a7c",
      scope = "email control_robots"
    )

    "endode response object to Json" in {
      response.asJson should be(responseJson)
    }

    "decode response Json to object" in {
      responseJson.as[Oauth2TokenResponse] should be(Right(response))
    }
  }
}
