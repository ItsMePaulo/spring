package com.example.kotlin.chat.service

import com.example.kotlin.chat.repository.ContentType
import com.example.kotlin.chat.repository.Message
import com.example.kotlin.chat.repository.MessageRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.net.URL

@Service
@Primary
class PersistenceMessageService(
    private val messageRepository: MessageRepository
) : MessageService {

    override fun latest(): List<MessageVM> =
        messageRepository.findLatest().map(this::convertMessageToMessageVm)

    override fun after(messageId: String): List<MessageVM> =
        messageRepository.findLatest(messageId).map(this::convertMessageToMessageVm)


    override fun post(message: MessageVM) {
        messageRepository.save(convertMessageVmToMessage(message))
    }

    private fun convertMessageToMessageVm(message: Message): MessageVM =
        with(message) {
            MessageVM(
                content = content,
                UserVM(username, URL(userAvatarImageLink)),
                sent = sent,
                id = id
            )
    }

    private fun convertMessageVmToMessage(messageVM: MessageVM) : Message =
        with(messageVM) {
            Message(
                content = content,
                contentType = ContentType.PLAIN,
                sent = sent,
                username = user.name,
                userAvatarImageLink = user.avatarImageLink.toString()
            )
        }
}