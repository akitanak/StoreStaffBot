package com.github.akitanak.storestaffbot.chatif.request.line.reply

import io.circe.Decoder.Result
import io.circe._
import io.circe.syntax._

sealed abstract class Template(val `type`: String)

object Template {
  implicit val encode: Encoder[Template] = new Encoder[Template] {
    override def apply(template: Template): Json =
      template match {
        case buttons: ButtonsTemplate => buttons.asJson
        case confirm: ConfirmTemplate => confirm.asJson
        case carousel: CarouselTemplate => carousel.asJson
        case imageCarousel: ImageCarouselTemplate => imageCarousel.asJson
      }
  }

  implicit val decode: Decoder[Template] = new Decoder[Template] {
    override def apply(c: HCursor): Result[Template] =
      c.downField("type").as[String] flatMap {
        case "buttons" => c.as[ButtonsTemplate]
        case "confirm" => c.as[ConfirmTemplate]
        case "carousel" => c.as[CarouselTemplate]
        case "image_carousel" => c.as[ImageCarouselTemplate]
      }
  }
}

case class ButtonsTemplate(thumbnailImageUrl: String, title: String, text: String, actions: Seq[Action]) extends Template("buttons")

object ButtonsTemplate {
  implicit val encode: Encoder[ButtonsTemplate] = new Encoder[ButtonsTemplate] {
    override final def apply(template: ButtonsTemplate): Json = Json.obj(
      "type" -> Json.fromString(template.`type`),
      "thumbnailImageUrl" -> Json.fromString(template.thumbnailImageUrl),
      "title" -> Json.fromString(template.title),
      "text" -> Json.fromString(template.text),
      "actions" -> template.actions.asJson
    )
  }

  implicit val decode: Decoder[ButtonsTemplate] = new Decoder[ButtonsTemplate] {
    override final def apply(c: HCursor): Result[ButtonsTemplate] =
      for {
        thumbnailImageUrl <- c.downField("thumbnailImageUrl").as[String]
        title <- c.downField("title").as[String]
        text <- c.downField("text").as[String]
        actions <- c.downField("actions").as[Seq[Action]]
      } yield {
        ButtonsTemplate(thumbnailImageUrl, title, text, actions)
      }
  }
}

case class ConfirmTemplate(text: String, actions: Seq[Action]) extends Template("confirm")

object ConfirmTemplate {
  implicit val encode: Encoder[ConfirmTemplate] = new Encoder[ConfirmTemplate] {
    override final def apply(template: ConfirmTemplate): Json = Json.obj(
      "type" -> Json.fromString(template.`type`),
      "text" -> Json.fromString(template.text),
      "actions" -> template.actions.asJson
    )
  }

  implicit val decode: Decoder[ConfirmTemplate] = new Decoder[ConfirmTemplate] {
    override final def apply(c: HCursor): Result[ConfirmTemplate] =
      for {
        text <- c.downField("text").as[String]
        actions <- c.downField("actions").as[Seq[Action]]
      } yield {
        ConfirmTemplate(text, actions)
      }
  }
}

case class CarouselTemplate(thumbnailImageUrl: String, title: String, text: String, actions: Seq[Action]) extends Template("carousel")

object CarouselTemplate {
  implicit val encode: Encoder[CarouselTemplate] = new Encoder[CarouselTemplate] {
    override final def apply(template: CarouselTemplate): Json = Json.obj(
      "type" -> Json.fromString(template.`type`),
      "thumbnailImageUrl" -> Json.fromString(template.thumbnailImageUrl),
      "title" -> Json.fromString(template.title),
      "text" -> Json.fromString(template.text),
      "actions" -> template.actions.asJson
    )
  }

  implicit val decode: Decoder[CarouselTemplate] = new Decoder[CarouselTemplate] {
    override final def apply(c: HCursor): Result[CarouselTemplate] =
      for {
        thumbnailImageUrl <- c.downField("thumbnailImageUrl").as[String]
        title <- c.downField("title").as[String]
        text <- c.downField("text").as[String]
        actions <- c.downField("actions").as[Seq[Action]]
      } yield {
        CarouselTemplate(thumbnailImageUrl, title, text, actions)
      }
  }
}

case class ImageCarouselTemplate(imageUrl: String, actions: Seq[Action]) extends Template("image_carousel")

object ImageCarouselTemplate {
  implicit val encode: Encoder[ImageCarouselTemplate] = new Encoder[ImageCarouselTemplate] {
    override final def apply(template: ImageCarouselTemplate): Json = Json.obj(
      "type" -> Json.fromString(template.`type`),
      "imageUrl" -> Json.fromString(template.imageUrl),
      "actions" -> template.actions.asJson
    )
  }

  implicit val decode: Decoder[ImageCarouselTemplate] = new Decoder[ImageCarouselTemplate] {
    override final def apply(c: HCursor): Result[ImageCarouselTemplate] =
      for {
        imageUrl <- c.downField("imageUrl").as[String]
        actions <- c.downField("actions").as[Seq[Action]]
      } yield {
        ImageCarouselTemplate(imageUrl, actions)
      }
  }
}

sealed abstract class Action(val `type`: String)

object Action {
  implicit val encode: Encoder[Action] = new Encoder[Action] {
    override final def apply(action: Action): Json =
      action match {
        case postback: PostbackAction => postback.asJson
        case message: MessageAction => message.asJson
        case uri: UriAction => uri.asJson
      }
  }

  implicit val decode: Decoder[Action] = new Decoder[Action] {
    override final def apply(c: HCursor): Decoder.Result[Action] =
      c.downField("type").as[String] flatMap {
        case "postback" => c.as[PostbackAction]
        case "message" => c.as[MessageAction]
        case "uri" => c.as[UriAction]
      }
  }
}

case class PostbackAction(label: String, data: String, text: String) extends Action("postback")

object PostbackAction {
  implicit val encode: Encoder[PostbackAction] = new Encoder[PostbackAction] {
    override final def apply(action: PostbackAction): Json = Json.obj(
      "type" -> Json.fromString(action.`type`),
      "label" -> Json.fromString(action.label),
      "data" -> Json.fromString(action.data),
      "text" -> Json.fromString(action.text)
    )
  }
  implicit val decode: Decoder[PostbackAction] = new Decoder[PostbackAction] {
    override final def apply(c: HCursor): Decoder.Result[PostbackAction] = {
      for {
        label <- c.downField("label").as[String]
        data <- c.downField("data").as[String]
        text <- c.downField("text").as[String]
      } yield {
        PostbackAction(label, data, text)
      }
    }
  }
}

case class MessageAction(label: String, text: String) extends Action("message")

object MessageAction {
  implicit val encode: Encoder[MessageAction] = new Encoder[MessageAction] {
    override final def apply(action: MessageAction): Json = Json.obj(
      "type" -> Json.fromString(action.`type`),
      "label" -> Json.fromString(action.label),
      "text" -> Json.fromString(action.text)
    )
  }

  implicit val decode: Decoder[MessageAction] = new Decoder[MessageAction] {
    override final def apply(c: HCursor): Decoder.Result[MessageAction] =
      for {
        label <- c.downField("label").as[String]
        text <- c.downField("text").as[String]
      } yield {
        MessageAction(label, text)
      }
  }
}

case class UriAction(label: String, uri: String) extends Action("uri")

object UriAction {
  implicit val encode: Encoder[UriAction] = new Encoder[UriAction] {
    override final def apply(action: UriAction): Json = Json.obj(
      "type" -> Json.fromString(action.`type`),
      "label" -> Json.fromString(action.label),
      "uri" -> Json.fromString(action.uri)
    )
  }

  implicit val decode: Decoder[UriAction] = new Decoder[UriAction] {
    override final def apply(c: HCursor): Decoder.Result[UriAction] =
      for {
        label <- c.downField("label").as[String]
        uri <- c.downField("uri").as[String]
      } yield {
        UriAction(label, uri)
      }
  }
}
