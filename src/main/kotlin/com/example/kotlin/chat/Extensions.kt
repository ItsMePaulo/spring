package com.example.kotlin.chat

import com.example.kotlin.chat.repository.ContentType
import com.example.kotlin.chat.repository.Message
import com.example.kotlin.chat.service.MessageVM
import com.example.kotlin.chat.service.UserVM
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import java.net.URL

fun List<Message>.toMessageVms(): List<MessageVM> =
    map { it.toMessageVm() }

fun Message.toMessageVm(): MessageVM =
    MessageVM(
        content = contentType.render(content),
        user = UserVM(username, URL(userAvatarImageLink)),
        sent = sent,
        id = id
    )

fun MessageVM.toMessage(contentType: ContentType = ContentType.MARKDOWN): Message =
    Message(
        content = content,
        contentType = contentType,
        sent = sent,
        username = user.name,
        userAvatarImageLink = user.avatarImageLink.toString()
    )

fun ContentType.render(content: String): String = when (this) {
    ContentType.PLAIN -> content
    ContentType.MARKDOWN -> {
        val flavour = CommonMarkFlavourDescriptor()
        HtmlGenerator(
            content, MarkdownParser(flavour).buildMarkdownTreeFromString(content),
            flavour
        ).generateHtml()
    }
}
