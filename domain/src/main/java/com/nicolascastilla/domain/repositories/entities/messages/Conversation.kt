package com.nicolascastilla.domain.repositories.entities.messages

data class Conversation(
    val idUser: String,
    val image: String,
    val message: String,
    val name: String,
    val timestamp:Long
)