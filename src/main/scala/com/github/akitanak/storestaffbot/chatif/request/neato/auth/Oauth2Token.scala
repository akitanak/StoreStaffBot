package com.github.akitanak.storestaffbot.chatif.request.neato.auth

import io.circe.{Encoder, _}

case class Oauth2TokenRequest(
  grantType: String,
  clientId: String,
  clientSecret: String,
  redirectUri: String,
  code: String
)

object Oauth2TokenRequest {

  implicit val encode: Encoder[Oauth2TokenRequest] =
    Encoder.forProduct5("grant_type", "client_id", "client_secret", "redirect_uri", "code")(r => (r.grantType, r.clientId, r.clientSecret, r.redirectUri, r.code))
  implicit val decode: Decoder[Oauth2TokenRequest] =
    Decoder.forProduct5("grant_type", "client_id", "client_secret", "redirect_uri", "code")(Oauth2TokenRequest.apply)

}

case class Oauth2TokenResponse(
  accessToken: String,
  tokenType: String,
  expiresIn: Int,
  refreshToken: String,
  scope: String
)

object Oauth2TokenResponse {

  implicit val encode: Encoder[Oauth2TokenResponse] =
    Encoder.forProduct5("access_token", "token_type", "expires_in", "refresh_token", "scope")(r => (r.accessToken, r.tokenType, r.expiresIn, r.refreshToken, r.scope))

  implicit val decode: Decoder[Oauth2TokenResponse] =
    Decoder.forProduct5("access_token", "token_type", "expires_in", "refresh_token", "scope")(Oauth2TokenResponse.apply)

}
