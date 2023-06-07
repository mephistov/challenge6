package com.nicolascastilla.domain.repositories.entities.contacts

data class ContactsEntity(
    val id:Long,
    val name:String,
    val phone: String,
    var isConected:Boolean = false,
    var isChecked:Boolean = false
)
