package com.nicolascastilla.domain.repositories.entities.messages

data class UserChatEntity(
    var messages: MutableList<Conversation>,
    val imgProfile: String,
    val lastMessage: String,
    val name: String,
    val timestamp: Long,
    val phone:String
)