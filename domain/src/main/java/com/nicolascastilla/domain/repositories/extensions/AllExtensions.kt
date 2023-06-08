package com.nicolascastilla.domain.repositories.extensions

import java.text.SimpleDateFormat
import java.util.*


fun Long.toHumanDate():String{
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date(this))
}

fun String.orderToFirebaseDb():String{
    val parts = this.split("_")
    if(parts.size < 2)
        return "ERROR"


    //val val1 = parts[0].replace(Regex("\\D"), "").toLong()
    val sortedList = parts.map {
        if(it == "")
            return "ERROR"
        it.replace(Regex("\\D"), "").toLong()
    }.sorted()
    var responde =""
    for(tel in sortedList){
        responde += "${tel}_"
    }

    return responde.dropLast(1)
}