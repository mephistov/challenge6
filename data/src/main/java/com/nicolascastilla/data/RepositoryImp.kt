package com.nicolascastilla.data

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nicolascastilla.data.local.contacts.GetContactsRepository
import com.nicolascastilla.data.local.dao.ChallengeDao
import com.nicolascastilla.data.local.preferences.UserPreferences
import com.nicolascastilla.domain.repositories.ChallengeRepository
import com.nicolascastilla.domain.repositories.entities.MessageItem
import com.nicolascastilla.domain.repositories.entities.contacts.ContactsEntity
import com.nicolascastilla.domain.repositories.entities.messages.Chats
import com.nicolascastilla.domain.repositories.entities.messages.Conversation
import com.nicolascastilla.domain.repositories.entities.messages.UserChatEntity
import com.nicolascastilla.domain.repositories.extensions.orderToFirebaseDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val dataBase: ChallengeDao,
    private val firebaseDatabase:DatabaseReference,
    private val userPreferences: UserPreferences,
    val contacts: GetContactsRepository,
): ChallengeRepository {

   // private val firebaseDatabase: DatabaseReference= Firebase.database.reference
    lateinit var referConversation : DatabaseReference

    override fun getAllData(): Flow<List<UserChatEntity>> = callbackFlow {
        val user = userPreferences.user.first()
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
               // val users = dataSnapshot//.children.mapNotNull { it.getValue(MessageItem::class.java) }
              //  val cant = users.childrenCount
             //   Log.e("TEST","cant: $cant")
                val listMessages:MutableList<UserChatEntity> = mutableListOf()
                user?.let {userData->

                    for (childSnapshot in dataSnapshot.children) {
                        val hijo = childSnapshot.key
                        hijo?.let {
                            val splitter = it.split("_")
                            for(item in splitter){
                                if(item == userData.phone){
                                    val tempData = childSnapshot.getValue(UserChatEntity::class.java)
                                    tempData?.let {ue->
                                        tempData.idChat = it
                                        listMessages.add(ue)
                                    }
                                }
                            }

                        }

                    }
                    trySend(listMessages).isSuccess
                }

                    // Aquí puedes obtener la clave y/o el valor de cada hijo
                    // Extract the non-conversation fields

            }

            override fun onCancelled(databaseError: DatabaseError) {
                close(databaseError.toException())
            }
        }

        firebaseDatabase.child("chats").addValueEventListener(listener)
        awaitClose { firebaseDatabase.removeEventListener(listener) }


    }

    override fun getAllContacts(): Flow<List<ContactsEntity>> {
        return contacts.getContacts()
    }

    override fun setupChatData(data: UserChatEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            val myuser = userPreferences.user.first()
            val myRef = myuser?.let {
                firebaseDatabase.child("chats").child((it.phone+"_"+data.phone).orderToFirebaseDb()).setValue(data)
            }
        }
    }

    override fun getAllConversation(data: UserChatEntity): Flow<List<Conversation>> = callbackFlow {
        val user = userPreferences.user
      //  val listMessages:MutableList<Conversation> = mutableListOf()
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listMessages:MutableList<Conversation> = mutableListOf()
                for (childSnapshot in dataSnapshot.children) {
                    val idUser = childSnapshot.child("idUser").getValue(String::class.java) ?: ""
                    val image = childSnapshot.child("image").getValue(String::class.java) ?: ""
                    val message = childSnapshot.child("message").getValue(String::class.java) ?: ""
                    val convName = childSnapshot.child("name").getValue(String::class.java) ?: ""
                    val time:Long = childSnapshot.child("timestamp").getValue(Long::class.java) ?: 0L

                    val conversation = Conversation(
                        idUser = idUser,
                        image = image,
                        message = message,
                        name = convName,
                        timestamp = time
                    )
                    listMessages.add(conversation)

                }

                trySend(listMessages).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        user.first()?.let {
            referConversation = firebaseDatabase
                .child("chats")
                .child(data.idChat)


            referConversation.child("messages").addValueEventListener(listener)


        }
        awaitClose { firebaseDatabase.removeEventListener(listener) }
    }

    override fun sendMessage(message: Conversation) {
        referConversation.child("messages").child(message.timestamp.toString()).setValue(message)
        referConversation.child("timestamp").setValue(message.timestamp)
        referConversation.child("lastMessage").setValue(message.message)
    }

}