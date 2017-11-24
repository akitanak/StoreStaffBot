package com.github.akitanak.storestaffbot.chatif.request.line.webhook

import io.circe._
import io.circe.syntax._

sealed abstract class Event(val `type`: String, timestamp: Long, source: Source)

object Event {
  implicit val encode: Encoder[Event] = new Encoder[Event] {
    override final def apply(event: Event): Json = {
      event match {
        case message: MessageEvent => message.asJson
        case follow:FollowEvent  => follow.asJson
        case unfollow: UnfollowEvent => unfollow.asJson
        case join: JoinEvent => join.asJson
        case leave: LeaveEvent => leave.asJson
        case postback: PostbackEvent => postback.asJson
        case beacon: BeaconEvent => beacon.asJson
      }
    }
  }

  implicit val decode: Decoder[Event] = new Decoder[Event] {
    override final def apply(c: HCursor): Decoder.Result[Event] = {
      c.downField("type").as[String] flatMap {
        case "message" => c.as[MessageEvent]
        case "follow" => c.as[FollowEvent]
        case "unfollow" => c.as[UnfollowEvent]
        case "join" => c.as[JoinEvent]
        case "leave" => c.as[LeaveEvent]
        case "postback" => c.as[PostbackEvent]
        case "beacon" => c.as[BeaconEvent]
      }
    }
  }
}


case class MessageEvent(replyToken: String, timestamp: Long, source: SourceUser, message: Message) extends Event("message", timestamp, source)

object MessageEvent {
  implicit val encode: Encoder[MessageEvent] = new Encoder[MessageEvent] {
    override final def apply(event: MessageEvent): Json = Json.obj(
      "type" -> Json.fromString(event.`type`),
      "replyToken" -> Json.fromString(event.replyToken),
      "timestamp" -> Json.fromLong(event.timestamp),
      "source" -> event.source.asJson,
      "message" -> event.message.asJson
    )
  }

  implicit val decode: Decoder[MessageEvent] = new Decoder[MessageEvent] {
    override final def apply(c: HCursor): Decoder.Result[MessageEvent] =
      for {
        replyToken <- c.downField("replyToken").as[String]
        timestamp <- c.downField("timestamp").as[Long]
        source <- c.downField("source").as[SourceUser]
        message <- c.downField("message").as[Message]
      } yield {
        MessageEvent(replyToken, timestamp, source, message)
      }
  }
}

case class FollowEvent(replyToken: String, timestamp: Long, source: SourceUser) extends Event("follow", timestamp, source)

object FollowEvent {
  implicit val encode: Encoder[FollowEvent] = new Encoder[FollowEvent] {
    override final def apply(event: FollowEvent): Json = Json.obj(
      "type" -> Json.fromString(event.`type`),
      "replyToken" -> Json.fromString(event.replyToken),
      "timestamp" -> Json.fromLong(event.timestamp),
      "source" -> event.source.asJson
    )
  }

  implicit val decode: Decoder[FollowEvent] = new Decoder[FollowEvent] {
    override final def apply(c: HCursor): Decoder.Result[FollowEvent] =
      for {
        replyToken <- c.downField("replyToken").as[String]
        timestamp <- c.downField("timestamp").as[Long]
        source <- c.downField("source").as[SourceUser]
      } yield {
        FollowEvent(replyToken, timestamp, source)
      }
  }
}

case class UnfollowEvent(replyToken: String, timestamp: Long, source: SourceUser) extends Event("unfollow", timestamp, source)

object UnfollowEvent {
  implicit val encode: Encoder[UnfollowEvent] = new Encoder[UnfollowEvent] {
    override final def apply(event: UnfollowEvent): Json = Json.obj(
      "type" -> Json.fromString(event.`type`),
      "replyToken" -> Json.fromString(event.replyToken),
      "timestamp" -> Json.fromLong(event.timestamp),
      "source" -> event.source.asJson
    )
  }

  implicit val decode: Decoder[UnfollowEvent] = new Decoder[UnfollowEvent] {
    override final def apply(c: HCursor): Decoder.Result[UnfollowEvent] =
      for {
        replyToken <- c.downField("replyToken").as[String]
        timestamp <- c.downField("timestamp").as[Long]
        source <- c.downField("source").as[SourceUser]
      } yield {
        UnfollowEvent(replyToken, timestamp, source)
      }
  }
}

case class JoinEvent(replyToken: String, timestamp: Long, source: SourceGroup) extends Event("join", timestamp, source)

object JoinEvent {
  implicit val encode: Encoder[JoinEvent] = new Encoder[JoinEvent] {
    override final def apply(event: JoinEvent): Json = Json.obj(
      "type" -> Json.fromString(event.`type`),
      "replyToken" -> Json.fromString(event.replyToken),
      "timestamp" -> Json.fromLong(event.timestamp),
      "source" -> event.source.asJson
    )
  }

  implicit val decode: Decoder[JoinEvent] = new Decoder[JoinEvent] {
    override final def apply(c: HCursor): Decoder.Result[JoinEvent] =
      for {
        replyToken <- c.downField("replyToken").as[String]
        timestamp <- c.downField("timestamp").as[Long]
        source <- c.downField("source").as[SourceGroup]
      } yield {
        JoinEvent(replyToken, timestamp, source)
      }
  }
}

case class LeaveEvent(replyToken: String, timestamp: Long, source: SourceGroup) extends Event("leave", timestamp, source)

object LeaveEvent {
  implicit val encode: Encoder[LeaveEvent] = new Encoder[LeaveEvent] {
    override final def apply(event: LeaveEvent): Json = Json.obj(
      "type" -> Json.fromString(event.`type`),
      "replyToken" -> Json.fromString(event.replyToken),
      "timestamp" -> Json.fromLong(event.timestamp),
      "source" -> event.source.asJson
    )
  }

  implicit val decode: Decoder[LeaveEvent] = new Decoder[LeaveEvent] {
    override final def apply(c: HCursor): Decoder.Result[LeaveEvent] =
      for {
        replyToken <- c.downField("replyToken").as[String]
        timestamp <- c.downField("timestamp").as[Long]
        source <- c.downField("source").as[SourceGroup]
      } yield {
        LeaveEvent(replyToken, timestamp, source)
      }
  }
}

case class PostbackEvent(replyToken: String, timestamp: Long, source: SourceUser, postback: Postback) extends Event("postback", timestamp, source)

object PostbackEvent {
  implicit val encode: Encoder[PostbackEvent] = new Encoder[PostbackEvent] {
    override final def apply(event: PostbackEvent): Json = Json.obj(
      "type" -> Json.fromString(event.`type`),
      "replyToken" -> Json.fromString(event.replyToken),
      "timestamp" -> Json.fromLong(event.timestamp),
      "source" -> event.source.asJson,
      "postback" -> event.postback.asJson
    )
  }

  implicit val decode: Decoder[PostbackEvent] = new Decoder[PostbackEvent] {
    override final def apply(c: HCursor): Decoder.Result[PostbackEvent] =
      for {
        replyToken <- c.downField("replyToken").as[String]
        timestamp <- c.downField("timestamp").as[Long]
        source <- c.downField("source").as[SourceUser]
        postback <- c.downField("postback").as[Postback]
      } yield {
        PostbackEvent(replyToken, timestamp, source, postback)
      }
  }
}

case class BeaconEvent(replyToken: String, timestamp: Long, source: SourceUser, beacon: Beacon) extends Event("beacon", timestamp, source)

object BeaconEvent {
  implicit val encode: Encoder[BeaconEvent] = new Encoder[BeaconEvent] {
    override final def apply(event: BeaconEvent): Json = Json.obj(
      "type" -> Json.fromString(event.`type`),
      "replyToken" -> Json.fromString(event.replyToken),
      "timestamp" -> Json.fromLong(event.timestamp),
      "source" -> event.source.asJson,
      "beacon" -> event.beacon.asJson
    )
  }

  implicit val decode: Decoder[BeaconEvent] = new Decoder[BeaconEvent] {
    override final def apply(c: HCursor): Decoder.Result[BeaconEvent] =
      for {
        replyToken <- c.downField("replyToken").as[String]
        timestamp <- c.downField("timestamp").as[Long]
        source <- c.downField("source").as[SourceUser]
        beacon <- c.downField("beacon").as[Beacon]
      } yield {
        BeaconEvent(replyToken, timestamp, source, beacon)
      }
  }
}