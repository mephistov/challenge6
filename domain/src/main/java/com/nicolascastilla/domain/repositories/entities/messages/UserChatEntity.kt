package com.nicolascastilla.domain.repositories.entities.messages

data class UserChatEntity(
    //var messages: MutableList<Conversation>,
    var imgProfile: String = "",
    val lastMessage: String = "",
    val name: String = "",
    val timestamp: Long = 0L,
    val phone:String = "",
    var idChat:String = "",
)