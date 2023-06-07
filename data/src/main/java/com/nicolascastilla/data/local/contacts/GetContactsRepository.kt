package com.nicolascastilla.data.local.contacts

import android.content.Context
import android.provider.ContactsContract
import com.nicolascastilla.domain.repositories.entities.contacts.ContactsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetContactsRepository @Inject constructor(
    private val context: Context,
)  {

    fun getContacts(): Flow<List<ContactsEntity>> = flow {

        val listContacts : MutableList<ContactsEntity> = mutableListOf()

        val cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        cursor?.use {
            val idIndex = it.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val hasPhoneNumberIndex = it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
            var idContact = 1L
            if (idIndex != -1 && nameIndex != -1 && hasPhoneNumberIndex != -1) {
                while (it.moveToNext()) {
                    val name = it.getString(nameIndex)
                    val hasPhoneNumber = it.getString(hasPhoneNumberIndex)

                    if (hasPhoneNumber == "1") {
                        val id = it.getString(idIndex)
                        val phoneCursor = context.contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(id),
                            null
                        )
                        phoneCursor?.use {
                            val phoneNumberIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            if (phoneNumberIndex != -1) {
                                while (phoneCursor.moveToNext()) {
                                    val phoneNumber = phoneCursor.getString(phoneNumberIndex)
                                    listContacts.add(ContactsEntity(
                                        id = idContact,
                                        name= name,
                                        phone = phoneNumber
                                    ))

                                }
                            }
                        }
                        idContact++
                        phoneCursor?.close()
                    }
                }
            }
        }
        cursor?.close()
        emit(listContacts)
    }
}