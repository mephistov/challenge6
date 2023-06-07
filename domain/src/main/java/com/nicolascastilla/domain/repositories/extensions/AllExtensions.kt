package com.nicolascastilla.domain.repositories.extensions

import java.text.SimpleDateFormat
import java.util.*


fun Long.toHumanDate():String{
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date(this))
}

fun String.orderToFirebaseDb():String{
    val parts = this.split("_")
    if(parts.size != 2)
        return "ERROR"
    if(parts[0] == "" || parts[1] == "")
        return "ERROR"

    val val1 = parts[0].replace(Regex("\\D"), "").toLong()
    val val2 = parts[1].replace(Regex("\\D"), "").toLong()

    var responde =""
    if(val1 < val2)
        responde = "${val1}_${val2}"
    else
        responde = "${val2}_${val1}"

    return responde
}