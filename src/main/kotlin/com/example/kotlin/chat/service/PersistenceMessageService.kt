package com.example.kotlin.chat.service

import com.example.kotlin.chat.repository.MessageRepository
import com.example.kotlin.chat.toMessage
import com.example.kotlin.chat.toMessageVms
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
@Primary
class PersistenceMessageService(
    private val messageRepository: MessageRepository
) : MessageService {

    override fun latest(): List<MessageVM> =
        messageRepository.findLatest().toMessageVms()

    override fun after(messageId: String): List<MessageVM> =
        messageRepository.findLatest(messageId).toMessageVms()


    override fun post(message: MessageVM) {
        messageRepository.save(message.toMessage())
    }
}