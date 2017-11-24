package com.github.akitanak.storestaffbot.chatif.request.line.webhook

import io.circe._
import io.circe.syntax._
import org.scalatest.{Matchers, WordSpec}

class EventSpec extends WordSpec with Matchers {

  "MessageEvent" should {
    "encode to JSON" in {

      // TextMessage
      val textMessageEvent = MessageEvent(
        replyToken = "_replyToken_",
        timestamp = Long.MaxValue,
        source = SourceUser(
          userId = "_userId_"
        ),
        message = TextMessage(
          id = "_id_",
          text = "_text_"
        )
      )

      textMessageEvent.asJson should be (
        Json.obj(
          "type" -> Json.fromString("message"),
          "replyToken" -> Json.fromString("_replyToken_"),
          "timestamp" -> Json.fromLong(Long.MaxValue),
          "source" -> Json.obj(
            "type" -> Json.fromString("user"),
            "userId" -> Json.fromString("_userId_")
          ),
          "message" -> Json.obj(
            "type" -> Json.fromString("text"),
            "id" -> Json.fromString("_id_"),
            "text" -> Json.fromString("_text_")
          )
        )
      )

      // ImageMessage
      val imageMessageEvent = textMessageEvent.copy(
        message = ImageMessage(id = "_id_")
      )

      imageMessageEvent.asJson should be (
        Json.obj(
          "type" -> Json.fromString("message"),
          "replyToken" -> Json.fromString("_replyToken_"),
          "timestamp" -> Json.fromLong(Long.MaxValue),
          "source" -> Json.obj(
            "type" -> Json.fromString("user"),
            "userId" -> Json.fromString("_userId_")
          ),
          "message" -> Json.obj(
            "type" -> Json.fromString("image"),
            "id" -> Json.fromString("_id_")
          )
        )
      )

      // VideoMessage
      val videoMessageEvent = textMessageEvent.copy(
        message = VideoMessage(id = "_id_")
      )

      videoMessageEvent.asJson should be (
        Json.obj(
          "type" -> Json.fromString("message"),
          "replyToken" -> Json.fromString("_replyToken_"),
          "timestamp" -> Json.fromLong(Long.MaxValue),
          "source" -> Json.obj(
            "type" -> Json.fromString("user"),
            "userId" -> Json.fromString("_userId_")
          ),
          "message" -> Json.obj(
            "type" -> Json.fromString("video"),
            "id" -> Json.fromString("_id_")
          )
        )
      )

      // AudioMessage
      val audioMessageEvent = textMessageEvent.copy(
        message = AudioMessage(id = "_id_")
      )

      audioMessageEvent.asJson should be (
        Json.obj(
          "type" -> Json.fromString("message"),
          "replyToken" -> Json.fromString("_replyToken_"),
          "timestamp" -> Json.fromLong(Long.MaxValue),
          "source" -> Json.obj(
            "type" -> Json.fromString("user"),
            "userId" -> Json.fromString("_userId_")
          ),
          "message" -> Json.obj(
            "type" -> Json.fromString("audio"),
            "id" -> Json.fromString("_id_")
          )
        )
      )

      // FileMessage
      val fileMessageEvent = textMessageEvent.copy(
        message = FileMessage(
          id = "_id_",
          fileName = "_fileName_",
          fileSize = "_fileSize_"
        )
      )

      fileMessageEvent.asJson should be (
        Json.obj(
          "type" -> Json.fromString("message"),
          "replyToken" -> Json.fromString("_replyToken_"),
          "timestamp" -> Json.fromLong(Long.MaxValue),
          "source" -> Json.obj(
            "type" -> Json.fromString("user"),
            "userId" -> Json.fromString("_userId_")
          ),
          "message" -> Json.obj(
            "type" -> Json.fromString("file"),
            "id" -> Json.fromString("_id_"),
            "fileName" -> Json.fromString("_fileName_"),
            "fileSize" -> Json.fromString("_fileSize_"),
          )
        )
      )
    }

    "decode from JSON" in {

      // TextMessage
      val json = Json.obj(
        "type" -> Json.fromString("message"),
        "replyToken" -> Json.fromString("_replyToken_"),
        "timestamp" -> Json.fromLong(Long.MaxValue),
        "source" -> Json.obj(
          "type" -> Json.fromString("user"),
          "userId" -> Json.fromString("_userId_")
        ),
        "message" -> Json.obj(
          "type" -> Json.fromString("text"),
          "id" -> Json.fromString("_id_"),
          "text" -> Json.fromString("_text_")
        )
      )

      json.as[MessageEvent] should be (
        Right(
          MessageEvent(
            replyToken = "_replyToken_",
            timestamp = Long.MaxValue,
            source = SourceUser(
              userId = "_userId_"
            ),
            message = TextMessage(
              id = "_id_",
              text = "_text_"
            )
          )
        )
      )

      // ImageMessage
      val imageJson = json.deepMerge(Json.obj(
        "message" -> Json.obj(
          "type" -> Json.fromString("image"),
          "id" -> Json.fromString("_id_")
        )
      ))

      imageJson.as[MessageEvent] should be (
        Right(
          MessageEvent(
            replyToken = "_replyToken_",
            timestamp = Long.MaxValue,
            source = SourceUser(
              userId = "_userId_"
            ),
            message = ImageMessage(
              id = "_id_"
            )
          )
        )
      )

      // VideoMessage
      val videoJson = json.deepMerge(Json.obj(
        "message" -> Json.obj(
          "type" -> Json.fromString("video"),
          "id" -> Json.fromString("_id_")
        )
      ))

      videoJson.as[MessageEvent] should be (
        Right(
          MessageEvent(
            replyToken = "_replyToken_",
            timestamp = Long.MaxValue,
            source = SourceUser(
              userId = "_userId_"
            ),
            message = VideoMessage(
              id = "_id_"
            )
          )
        )
      )

      // AudioMessage
      val audioJson = json.deepMerge(Json.obj(
        "message" -> Json.obj(
          "type" -> Json.fromString("audio"),
          "id" -> Json.fromString("_id_")
        )
      ))

      audioJson.as[MessageEvent] should be (
        Right(
          MessageEvent(
            replyToken = "_replyToken_",
            timestamp = Long.MaxValue,
            source = SourceUser(
              userId = "_userId_"
            ),
            message = AudioMessage(
              id = "_id_"
            )
          )
        )
      )

      // FileMessage
      val fileJson = json.deepMerge(Json.obj(
        "message" -> Json.obj(
          "type" -> Json.fromString("file"),
          "id" -> Json.fromString("_id_"),
          "fileName" -> Json.fromString("_fileName_"),
          "fileSize" -> Json.fromString("_fileSize_")
        )
      ))

      fileJson.as[MessageEvent] should be (
        Right(
          MessageEvent(
            replyToken = "_replyToken_",
            timestamp = Long.MaxValue,
            source = SourceUser(
              userId = "_userId_"
            ),
            message = FileMessage(
              id = "_id_",
              fileName = "_fileName_",
              fileSize = "_fileSize_"
            )
          )
        )
      )

      // LocationMessage
      val locationJson = json.deepMerge(Json.obj(
        "message" -> Json.obj(
          "type" -> Json.fromString("location"),
          "id" -> Json.fromString("_id_"),
          "title" -> Json.fromString("_title_"),
          "address" -> Json.fromString("_address_"),
          "latitude" -> Json.fromString("_latitude_"),
          "longitude" -> Json.fromString("_longitude_"),
        )
      ))

      locationJson.as[MessageEvent] should be (
        Right(
          MessageEvent(
            replyToken = "_replyToken_",
            timestamp = Long.MaxValue,
            source = SourceUser(
              userId = "_userId_"
            ),
            message = LocationMessage(
              id = "_id_",
              title = "_title_",
              address = "_address_",
              latitude = "_latitude_",
              longitude = "_longitude_",
            )
          )
        )
      )

      // StickerMessage
      val stickerJson = json.deepMerge(Json.obj(
        "message" -> Json.obj(
          "type" -> Json.fromString("sticker"),
          "id" -> Json.fromString("_id_"),
          "packageId" -> Json.fromString("_packageId_"),
          "stickerId" -> Json.fromString("_stickerId_"),
        )
      ))

      stickerJson.as[MessageEvent] should be (
        Right(
          MessageEvent(
            replyToken = "_replyToken_",
            timestamp = Long.MaxValue,
            source = SourceUser(
              userId = "_userId_"
            ),
            message = StickerMessage(
              id = "_id_",
              packageId = "_packageId_",
              stickerId = "_stickerId_"
            )
          )
        )
      )
    }
  }

  "FollowEvent" should {
    "encode to JSON" in {
      val event = FollowEvent(
        replyToken = "_replyToken_",
        timestamp = Long.MaxValue,
        source = SourceUser(
          userId = "_userId_"
        )
      )

      event.asJson should be (
        Json.obj(
          "type" -> Json.fromString("follow"),
          "replyToken" -> Json.fromString("_replyToken_"),
          "timestamp" -> Json.fromLong(Long.MaxValue),
          "source" -> Json.obj(
            "type" -> Json.fromString("user"),
            "userId" -> Json.fromString("_userId_")
          )
        )
      )

    }

    "decode from JSON" in {
      val json = Json.obj(
        "type" -> Json.fromString("follow"),
        "replyToken" -> Json.fromString("_replyToken_"),
        "timestamp" -> Json.fromLong(Long.MaxValue),
        "source" -> Json.obj(
          "type" -> Json.fromString("user"),
          "userId" -> Json.fromString("_userId_")
        )
      )

      json.as[FollowEvent] should be (
        Right(
          FollowEvent(
            replyToken = "_replyToken_",
            timestamp = Long.MaxValue,
            source = SourceUser(
              userId = "_userId_"
            )
          )
        )
      )
    }
  }

  "UnfollowEvent" should {
    "encode to JSON" in {
      val event = UnfollowEvent(
        replyToken = "_replyToken_",
        timestamp = Long.MaxValue,
        source = SourceUser(
          userId = "_userId_"
        )
      )

      event.asJson should be (
        Json.obj(
          "type" -> Json.fromString("unfollow"),
          "replyToken" -> Json.fromString("_replyToken_"),
          "timestamp" -> Json.fromLong(Long.MaxValue),
          "source" -> Json.obj(
            "type" -> Json.fromString("user"),
            "userId" -> Json.fromString("_userId_")
          )
        )
      )

    }

    "decode from JSON" in {
      val json = Json.obj(
        "type" -> Json.fromString("unfollow"),
        "replyToken" -> Json.fromString("_replyToken_"),
        "timestamp" -> Json.fromLong(Long.MaxValue),
        "source" -> Json.obj(
          "type" -> Json.fromString("user"),
          "userId" -> Json.fromString("_userId_")
        )
      )

      json.as[UnfollowEvent] should be (
        Right(
          UnfollowEvent(
            replyToken = "_replyToken_",
            timestamp = Long.MaxValue,
            source = SourceUser(
              userId = "_userId_"
            )
          )
        )
      )
    }
  }

  "JoinEvent" should {
    "encode to JSON" in {
      val event = JoinEvent(
        replyToken = "_replyToken_",
        timestamp = Long.MaxValue,
        source = SourceGroup(
          groupId = "_groupId_",
          userId = "_userId_"
        )
      )

      event.asJson should be (
        Json.obj(
          "type" -> Json.fromString("join"),
          "replyToken" -> Json.fromString("_replyToken_"),
          "timestamp" -> Json.fromLong(Long.MaxValue),
          "source" -> Json.obj(
            "type" -> Json.fromString("group"),
            "groupId" -> Json.fromString("_groupId_"),
            "userId" -> Json.fromString("_userId_")
          )
        )
      )

    }

    "decode from JSON" in {
      val json = Json.obj(
        "type" -> Json.fromString("join"),
        "replyToken" -> Json.fromString("_replyToken_"),
        "timestamp" -> Json.fromLong(Long.MaxValue),
        "source" -> Json.obj(
          "type" -> Json.fromString("group"),
          "groupId" -> Json.fromString("_groupId_"),
          "userId" -> Json.fromString("_userId_")
        )
      )

      json.as[JoinEvent] should be (
        Right(
          JoinEvent(
            replyToken = "_replyToken_",
            timestamp = Long.MaxValue,
            source = SourceGroup(
              groupId = "_groupId_",
              userId = "_userId_"
            )
          )
        )
      )
    }
  }

  "LeaveEvent" should {
    "encode to JSON" in {
      val event = LeaveEvent(
        replyToken = "_replyToken_",
        timestamp = Long.MaxValue,
        source = SourceGroup(
          groupId = "_groupId_",
          userId = "_userId_"
        )
      )

      event.asJson should be (
        Json.obj(
          "type" -> Json.fromString("leave"),
          "replyToken" -> Json.fromString("_replyToken_"),
          "timestamp" -> Json.fromLong(Long.MaxValue),
          "source" -> Json.obj(
            "type" -> Json.fromString("group"),
            "groupId" -> Json.fromString("_groupId_"),
            "userId" -> Json.fromString("_userId_")
          )
        )
      )

    }

    "decode from JSON" in {
      val json = Json.obj(
        "type" -> Json.fromString("leave"),
        "replyToken" -> Json.fromString("_replyToken_"),
        "timestamp" -> Json.fromLong(Long.MaxValue),
        "source" -> Json.obj(
          "type" -> Json.fromString("group"),
          "groupId" -> Json.fromString("_groupId_"),
          "userId" -> Json.fromString("_userId_")
        )
      )

      json.as[LeaveEvent] should be (
        Right(
          LeaveEvent(
            replyToken = "_replyToken_",
            timestamp = Long.MaxValue,
            source = SourceGroup(
              groupId = "_groupId_",
              userId = "_userId_"
            )
          )
        )
      )
    }
  }

  "PostbackEvent" should {
    "encode to JSON" in {
      val event = PostbackEvent(
        replyToken = "_replyToken_",
        timestamp = Long.MaxValue,
        source = SourceUser(
          userId = "_userId_"
        ),
        postback = Postback(
          data = "_data_",
          params = PostbackParams(
            date = "2017-12-12",
            time = "12:12",
            datetime = "2017-12-12T12:12"
          )
        )
      )

      event.asJson should be (
        Json.obj(
          "type" -> Json.fromString("postback"),
          "replyToken" -> Json.fromString("_replyToken_"),
          "timestamp" -> Json.fromLong(Long.MaxValue),
          "source" -> Json.obj(
            "type" -> Json.fromString("user"),
            "userId" -> Json.fromString("_userId_")
          ),
          "postback" -> Json.obj(
            "data" -> Json.fromString("_data_"),
            "params" -> Json.obj(
              "date" -> Json.fromString("2017-12-12"),
              "time" -> Json.fromString("12:12"),
              "datetime" -> Json.fromString("2017-12-12T12:12")
            )
          )
        )
      )

    }

    "decode from JSON" in {
      val json = Json.obj(
        "type" -> Json.fromString("postback"),
        "replyToken" -> Json.fromString("_replyToken_"),
        "timestamp" -> Json.fromLong(Long.MaxValue),
        "source" -> Json.obj(
          "type" -> Json.fromString("user"),
          "userId" -> Json.fromString("_userId_")
        ),
        "postback" -> Json.obj(
          "data" -> Json.fromString("_data_"),
          "params" -> Json.obj(
            "date" -> Json.fromString("2017-12-12"),
            "time" -> Json.fromString("12:12"),
            "datetime" -> Json.fromString("2017-12-12T12:12")
          )
        )
      )

      json.as[PostbackEvent] should be (
        Right(
          PostbackEvent(
            replyToken = "_replyToken_",
            timestamp = Long.MaxValue,
            source = SourceUser(
              userId = "_userId_"
            ),
            postback = Postback(
              data = "_data_",
              params = PostbackParams(
                date = "2017-12-12",
                time = "12:12",
                datetime = "2017-12-12T12:12"
              )
            )
          )
        )
      )
    }
  }

  "Event" should {
    "encode to JSON" in {
      val event = MessageEvent(
        replyToken = "_replyToken_",
        timestamp = Long.MaxValue,
        source = SourceUser(
          userId = "_userId_"
        ),
        message = TextMessage(
          id = "_id_",
          text = "_text_"
        )
      ).asInstanceOf[Event]

      event.asJson should be(
        Json.obj(
          "type" -> Json.fromString("message"),
          "replyToken" -> Json.fromString("_replyToken_"),
          "timestamp" -> Json.fromLong(Long.MaxValue),
          "source" -> Json.obj(
            "type" -> Json.fromString("user"),
            "userId" -> Json.fromString("_userId_")
          ),
          "message" -> Json.obj(
            "type" -> Json.fromString("text"),
            "id" -> Json.fromString("_id_"),
            "text" -> Json.fromString("_text_")
          )
        )
      )
    }

    "decode from JSON" in {
      val json = Json.obj(
        "type" -> Json.fromString("message"),
        "replyToken" -> Json.fromString("_replyToken_"),
        "timestamp" -> Json.fromLong(Long.MaxValue),
        "source" -> Json.obj(
          "type" -> Json.fromString("user"),
          "userId" -> Json.fromString("_userId_")
        ),
        "message" -> Json.obj(
          "type" -> Json.fromString("text"),
          "id" -> Json.fromString("_id_"),
          "text" -> Json.fromString("_text_")
        )
      )

      json.as[Event] should be(
        Right(
          MessageEvent(
            replyToken = "_replyToken_",
            timestamp = Long.MaxValue,
            source = SourceUser(
              userId = "_userId_"
            ),
            message = TextMessage(
              id = "_id_",
              text = "_text_"
            )
          )
        )
      )
    }
  }
}
