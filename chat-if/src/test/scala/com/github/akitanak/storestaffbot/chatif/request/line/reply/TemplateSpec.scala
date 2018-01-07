package com.github.akitanak.storestaffbot.chatif.request.line.reply

import io.circe._
import io.circe.syntax._
import org.scalatest.{Matchers, WordSpec}

class TemplateSpec extends WordSpec with Matchers {

  val actions = Seq(
    PostbackAction("_label_", "_data_", "_text_"),
    MessageAction("_label_", "_text_"),
    UriAction("_label_", "_uri_")
  )

  val actionsJson = Json.arr(
    Json.obj(
      "type" -> Json.fromString("postback"),
      "label" -> Json.fromString("_label_"),
      "data" -> Json.fromString("_data_"),
      "text" -> Json.fromString("_text_")
    ),
    Json.obj(
      "type" -> Json.fromString("message"),
      "label" -> Json.fromString("_label_"),
      "text" -> Json.fromString("_text_")
    ),
    Json.obj(
      "type" -> Json.fromString("uri"),
      "label" -> Json.fromString("_label_"),
      "uri" -> Json.fromString("_uri_")
    )
  )

  "ButtonsTemplate" should {
    val template = ButtonsTemplate(
      thumbnailImageUrl = "_url_",
      title = "_title_",
      text = "_text_",
      actions = actions
    )

    val json = Json.obj(
      "type" -> Json.fromString("buttons"),
      "thumbnailImageUrl" -> Json.fromString("_url_"),
      "title" -> Json.fromString("_title_"),
      "text" -> Json.fromString("_text_"),
      "actions" -> actionsJson
    )

    "encode to JSON" in {
      template.asJson should be(json)
    }

    "decode from JSON" in {
      json.as[ButtonsTemplate] should be(Right(template))
    }
  }

  "ConfirmTemplate" should {
    val template = ConfirmTemplate(
      text = "_text_",
      actions = actions
    )

    val json = Json.obj(
      "type" -> Json.fromString("confirm"),
      "text" -> Json.fromString("_text_"),
      "actions" -> actionsJson
    )

    "encode to JSON" in {
      template.asJson should be(json)
    }

    "decode from JSON" in {
      json.as[ConfirmTemplate] should be(Right(template))
    }
  }

  "CarouselTemplate" should {
    val template = CarouselColumn(
      thumbnailImageUrl = "_url_",
      title = "_title_",
      text = "_text_",
      actions = actions
    )

    val json = Json.obj(
      "type" -> Json.fromString("carousel"),
      "thumbnailImageUrl" -> Json.fromString("_url_"),
      "title" -> Json.fromString("_title_"),
      "text" -> Json.fromString("_text_"),
      "actions" -> actionsJson
    )

    "encode to JSON" in {
      template.asJson should be(json)
    }

    "decode from JSON" in {
      json.as[CarouselColumn] should be(Right(template))
    }
  }

  "ImageCarouselTemplate" should {
    val template = ImageCarouselTemplate(
      imageUrl = "_url_",
      actions = actions
    )

    val json = Json.obj(
      "type" -> Json.fromString("image_carousel"),
      "imageUrl" -> Json.fromString("_url_"),
      "actions" -> actionsJson
    )

    "encode to JSON" in {
      template.asJson should be(json)
    }

    "decode from JSON" in {
      json.as[ImageCarouselTemplate] should be(Right(template))
    }
  }

  "Template" should {
    val template = ConfirmTemplate(
      text = "_text_",
      actions = actions
    ).asInstanceOf[Template]

    val json = Json.obj(
      "type" -> Json.fromString("confirm"),
      "text" -> Json.fromString("_text_"),
      "actions" -> actionsJson
    )

    "encode to JSON" in {
      template.asJson should be(json)
    }

    "decode from JSON" in {
      json.as[Template] should be(Right(template))
    }
  }

  "PostbackAction" should {

    val postbackAction = PostbackAction(
      label = "_label_",
      data = "_data_",
      text = "_text_"
    )

    val postbackJson = Json.obj(
      "type" -> Json.fromString("postback"),
      "label" -> Json.fromString("_label_"),
      "data" -> Json.fromString("_data_"),
      "text" -> Json.fromString("_text_")
    )

    "encode to JSON" in {
      postbackAction.asJson should be(postbackJson)
    }

    "decode from JSON" in {
      postbackJson.as[PostbackAction] should be(Right(postbackAction))
    }
  }

  "MessageAction" should {

    val messageAction = MessageAction(
      label = "_label_",
      text = "_text_"
    )

    val messageJson = Json.obj(
      "type" -> Json.fromString("message"),
      "label" -> Json.fromString("_label_"),
      "text" -> Json.fromString("_text_")
    )

    "encode to JSON" in {
      messageAction.asJson should be(messageJson)
    }

    "decode from JSON" in {
      messageJson.as[MessageAction] should be(Right(messageAction))
    }
  }

  "UriAction" should {

    val uriAction = UriAction(
      label = "_label_",
      uri = "_uri_"
    )

    val uriJson = Json.obj(
      "type" -> Json.fromString("uri"),
      "label" -> Json.fromString("_label_"),
      "uri" -> Json.fromString("_uri_")
    )

    "encode to JSON" in {
      uriAction.asJson should be(uriJson)
    }

    "decode from JSON" in {
      uriJson.as[UriAction] should be(Right(uriAction))
    }
  }

  "Action" should {

    val action = MessageAction(
      label = "_label_",
      text = "_text_"
    ).asInstanceOf[Action]

    val json = Json.obj(
      "type" -> Json.fromString("message"),
      "label" -> Json.fromString("_label_"),
      "text" -> Json.fromString("_text_")
    )

    "encode to JSON" in {
      action.asJson should be(json)
    }

    "decode from JSON" in {
      json.as[Action] should be(Right(action))
    }
  }
}
