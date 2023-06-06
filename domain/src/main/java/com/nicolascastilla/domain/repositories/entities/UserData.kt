package com.nicolascastilla.domain.repositories.entities

data class UserData(
    val systemId:String,
    val name:String,
    val emai:String,
    val password:String,
    var phone:String,
    var uid:String
)
