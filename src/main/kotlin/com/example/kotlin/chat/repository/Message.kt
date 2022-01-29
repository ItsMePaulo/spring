package com.example.kotlin.chat.repository

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("MESSAGES")
data class Message (
    @Id
    var id: String? = null,
    val content: String,
    val contentType: ContentType,
    val sent: Instant,
    val username: String,
    val userAvatarImageLink: String,
)

enum class ContentType {
    PLAIN
}