package com.nicolascastilla.domain.repositories.entities.messages

data class UserChatEntity(
    val chats: Chats,
    val imgProfile: String,
    val lastMessage: String,
    val name: String,
    val timestamp: Int
)