package com.github.akitanak.storestaffbot.chatif.request.line.webhook

import io.circe._
import io.circe.syntax._

sealed abstract class Message(val id: String, val `type`: String)

object Message {
  implicit val encode: Encoder[Message] = new Encoder[Message] {
    override final def apply(message: Message): Json = {
      message.`type` match {
        case "text" => message.asInstanceOf[TextMessage].asJson
        case "image" => message.asInstanceOf[ImageMessage].asJson
        case "video" => message.asInstanceOf[VideoMessage].asJson
        case "audio" => message.asInstanceOf[AudioMessage].asJson
        case "file" => message.asInstanceOf[FileMessage].asJson
        case "location" => message.asInstanceOf[LocationMessage].asJson
        case "sticker" => message.asInstanceOf[StickerMessage].asJson
      }
    }
  }

  implicit val decode: Decoder[Message] = new Decoder[Message] {
    override final def apply(c: HCursor): Decoder.Result[Message] = {
      c.downField("type").as[String].flatMap {
        case "text" => c.as[TextMessage]
        case "image" => c.as[ImageMessage]
        case "video" => c.as[VideoMessage]
        case "audio" => c.as[AudioMessage]
        case "file" => c.as[FileMessage]
        case "location" => c.as[LocationMessage]
        case "sticker" => c.as[StickerMessage]
      }
    }
  }
}

case class TextMessage(override val id: String, text: String) extends Message(id, "text")

object TextMessage {
  implicit val encode: Encoder[TextMessage] = new Encoder[TextMessage] {
    override final def apply(message: TextMessage): Json = Json.obj(
      "type" -> Json.fromString(message.`type`),
      "id" -> Json.fromString(message.id),
      "text" -> Json.fromString(message.text),
    )
  }

  implicit val decode: Decoder[TextMessage] = new Decoder[TextMessage] {
    override final def apply(c: HCursor): Decoder.Result[TextMessage] =
      for {
        id <- c.downField("id").as[String]
        text <- c.downField("text").as[String]
      } yield {
        TextMessage(id, text)
      }
  }
}

case class ImageMessage(override val id: String) extends Message(id, "image")

object ImageMessage {
  implicit val encode: Encoder[ImageMessage] = new Encoder[ImageMessage] {
    override final def apply(message: ImageMessage): Json = Json.obj(
      "type" -> Json.fromString(message.`type`),
      "id" -> Json.fromString(message.id),
    )
  }

  implicit val decode: Decoder[ImageMessage] = new Decoder[ImageMessage] {
    override final def apply(c: HCursor): Decoder.Result[ImageMessage] =
      for {
        id <- c.downField("id").as[String]
      } yield {
        ImageMessage(id)
      }
  }
}

case class VideoMessage(override val id: String) extends Message(id, "video")

object VideoMessage {
  implicit val encode: Encoder[VideoMessage] = new Encoder[VideoMessage] {
    override final def apply(message: VideoMessage): Json = Json.obj(
      "type" -> Json.fromString(message.`type`),
      "id" -> Json.fromString(message.id),
    )
  }

  implicit val decode: Decoder[VideoMessage] = new Decoder[VideoMessage] {
    override final def apply(c: HCursor): Decoder.Result[VideoMessage] =
      for {
        id <- c.downField("id").as[String]
      } yield {
        VideoMessage(id)
      }
  }
}

case class AudioMessage(override val id: String) extends Message(id, "audio")

object AudioMessage {
  implicit val encode: Encoder[AudioMessage] = new Encoder[AudioMessage] {
    override final def apply(message: AudioMessage): Json = Json.obj(
      "type" -> Json.fromString(message.`type`),
      "id" -> Json.fromString(message.id),
    )
  }

  implicit val decode: Decoder[AudioMessage] = new Decoder[AudioMessage] {
    override final def apply(c: HCursor): Decoder.Result[AudioMessage] =
      for {
        id <- c.downField("id").as[String]
      } yield {
        AudioMessage(id)
      }
  }
}

case class FileMessage(override val id: String, fileName: String, fileSize: String) extends Message(id, "file")

object FileMessage {
  implicit val encode: Encoder[FileMessage] = new Encoder[FileMessage] {
    override final def apply(message: FileMessage): Json = Json.obj(
      "type" -> Json.fromString(message.`type`),
      "id" -> Json.fromString(message.id),
      "fileName" -> Json.fromString(message.fileName),
      "fileSize" -> Json.fromString(message.fileSize),
    )
  }

  implicit val decode: Decoder[FileMessage] = new Decoder[FileMessage] {
    override final def apply(c: HCursor): Decoder.Result[FileMessage] =
      for {
        id <- c.downField("id").as[String]
        fileName <- c.downField("fileName").as[String]
        fileSize <- c.downField("fileSize").as[String]
      } yield {
        FileMessage(id, fileName, fileSize)
      }
  }
}

case class LocationMessage(override val id: String, title: String, address: String, latitude: String, longitude: String) extends Message(id, "location")

object LocationMessage {
  implicit val encode: Encoder[LocationMessage] = new Encoder[LocationMessage] {
    override final def apply(message: LocationMessage): Json = Json.obj(
      "type" -> Json.fromString(message.`type`),
      "id" -> Json.fromString(message.id),
      "title" -> Json.fromString(message.title),
      "address" -> Json.fromString(message.address),
      "latitude" -> Json.fromString(message.latitude),
      "longitude" -> Json.fromString(message.longitude),
    )
  }

  implicit val decode: Decoder[LocationMessage] = new Decoder[LocationMessage] {
    override final def apply(c: HCursor): Decoder.Result[LocationMessage] =
      for {
        id <- c.downField("id").as[String]
        title <- c.downField("title").as[String]
        address <- c.downField("address").as[String]
        latitude <- c.downField("latitude").as[String]
        longitude <- c.downField("longitude").as[String]
      } yield {
        LocationMessage(id, title, address, latitude, longitude)
      }
  }
}

case class StickerMessage(override val id: String, packageId: String, stickerId: String) extends Message(id, "sticker")

object StickerMessage {
  implicit val encode: Encoder[StickerMessage] = new Encoder[StickerMessage] {
    override final def apply(message: StickerMessage): Json = Json.obj(
      "type" -> Json.fromString(message.`type`),
      "id" -> Json.fromString(message.id),
      "packageId" -> Json.fromString(message.packageId),
      "stickerId" -> Json.fromString(message.stickerId),
    )
  }

  implicit val decode: Decoder[StickerMessage] = new Decoder[StickerMessage] {
    override final def apply(c: HCursor): Decoder.Result[StickerMessage] =
      for {
        id <- c.downField("id").as[String]
        packageId <- c.downField("packageId").as[String]
        stickerId <- c.downField("stickerId").as[String]
      } yield {
        StickerMessage(id, packageId, stickerId)
      }
  }
}