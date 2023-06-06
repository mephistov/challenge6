package com.nicolascastilla.challenge6.composables

import android.provider.ContactsContract
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun NewChatsComposable() {
    //TODO pedir permiso de lista usuarios
    //TODO mostrar lista de usuarios y validar cuales existen en base datos firebase
    //TODO colocar boton de sync en head
    //TODO si vacio datos bd trer de la lista de android
    //TODO revisar numero tras numero si existe en firebase
    //TODO guardar en bd
    ShowContacts()
}

@Composable
fun ShowContacts() {
    val context = LocalContext.current
    val contacts = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        val cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        cursor?.use {
            val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            if (nameIndex != -1) {
                while (it.moveToNext()) {
                    val name = it.getString(nameIndex)
                    contacts.add(name)
                }
            }
        }
    }

    LazyColumn {
        items(contacts) { contact ->
            Text(text = contact)
        }
    }
}