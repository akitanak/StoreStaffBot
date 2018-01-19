package com.github.akitanak.storestaffbot.chatif.request.line.webhook

import io.circe._

sealed abstract class Source(val `type`: String, userId: String)

object Source {

}

case class SourceUser(userId: String) extends Source("user", userId)

object SourceUser {
  implicit val encoder: Encoder[SourceUser] = new Encoder[SourceUser] {
    override final def apply(source: SourceUser): Json = Json.obj(
      "type" -> Json.fromString(source.`type`),
      "userId" -> Json.fromString(source.userId),
    )
  }
  implicit val decoder: Decoder[SourceUser] = new Decoder[SourceUser] {
    override final def apply(c: HCursor): Decoder.Result[SourceUser] =
      for {
        userId <- c.downField("userId").as[String]
      } yield {
        SourceUser(userId)
      }
  }
}

case class SourceGroup(groupId: String, userId: String) extends Source("group", userId)

object SourceGroup {
  implicit val encoder: Encoder[SourceGroup] = new Encoder[SourceGroup] {
    override final def apply(source: SourceGroup): Json = Json.obj(
      "type" -> Json.fromString(source.`type`),
      "groupId" -> Json.fromString(source.groupId),
      "userId" -> Json.fromString(source.userId),
    )
  }
  implicit val decoder: Decoder[SourceGroup] = new Decoder[SourceGroup] {
    override final def apply(c: HCursor): Decoder.Result[SourceGroup] =
      for {
        groupId <- c.downField("groupId").as[String]
        userId <- c.downField("userId").as[String]
      } yield {
        SourceGroup(groupId, userId)
      }
  }
}

case class SourceRoom(roomId: String, userId: String) extends Source("room", userId)

object SourceRoom {
  implicit val encoder: Encoder[SourceRoom] = new Encoder[SourceRoom] {
    override final def apply(source: SourceRoom): Json = Json.obj(
      "type" -> Json.fromString(source.`type`),
      "roomId" -> Json.fromString(source.roomId),
      "userId" -> Json.fromString(source.userId),
    )
  }
  implicit val decoder: Decoder[SourceRoom] = new Decoder[SourceRoom] {
    override final def apply(c: HCursor): Decoder.Result[SourceRoom] =
      for {
        roomId <- c.downField("roomId").as[String]
        userId <- c.downField("userId").as[String]
      } yield {
        SourceRoom(roomId, userId)
      }
  }
}