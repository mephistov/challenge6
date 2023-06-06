package com.nicolascastilla.data

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nicolascastilla.data.local.dao.ChallengeDao
import com.nicolascastilla.data.local.preferences.UserPreferences
import com.nicolascastilla.domain.repositories.ChallengeRepository
import com.nicolascastilla.domain.repositories.entities.MessageItem
import com.nicolascastilla.domain.repositories.entities.messages.Chats
import com.nicolascastilla.domain.repositories.entities.messages.Conversation
import com.nicolascastilla.domain.repositories.entities.messages.UserChatEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val dataBase: ChallengeDao,
    private val firebaseDatabase:DatabaseReference,
    private val userPreferences: UserPreferences
): ChallengeRepository {

   // private val firebaseDatabase: DatabaseReference= Firebase.database.reference

    override fun getAllData(): Flow<List<UserChatEntity>> = callbackFlow {
        val user = userPreferences.user
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users = dataSnapshot//.children.mapNotNull { it.getValue(MessageItem::class.java) }
                val cant = users.childrenCount
                Log.e("TEST","cant: $cant")

                val listMessages:MutableList<UserChatEntity> = mutableListOf()
                for (childSnapshot in dataSnapshot.children) {
                    // Aquí puedes obtener la clave y/o el valor de cada hijo
                    // Extract the non-conversation fields
                    val imgProfile = childSnapshot.child("imgProfile").getValue(String::class.java) ?: ""
                    val lastMessage = childSnapshot.child("lastMessage").getValue(String::class.java) ?: ""
                    val name = childSnapshot.child("name").getValue(String::class.java) ?: ""
                    val timestamp = childSnapshot.child("timestamp").getValue(Int::class.java) ?: 0

                    // Extract the conversations
                    val conversations: MutableList<Conversation> = mutableListOf()
                    val chatsSnapshot = childSnapshot.child("chats")
                    for (chatSnapshot in chatsSnapshot.children) {
                        val idUser = chatSnapshot.child("idUser").getValue(String::class.java) ?: ""
                        val image = chatSnapshot.child("image").getValue(String::class.java) ?: ""
                        val message = chatSnapshot.child("message").getValue(String::class.java) ?: ""
                        val convName = chatSnapshot.child("name").getValue(String::class.java) ?: ""

                        val conversation = Conversation(idUser, image, message, convName)
                        conversations.add(conversation)
                    }

                    // Construct and add the UserChatEntity
                    val userChatEntity = UserChatEntity(Chats(conversations), imgProfile, lastMessage, name, timestamp)
                    listMessages.add(userChatEntity)

                }

                trySend(listMessages).isSuccess

            }

            override fun onCancelled(databaseError: DatabaseError) {
                close(databaseError.toException())
            }
        }
        user.first()?.let {
            firebaseDatabase.child("chats").child(it.uid).addValueEventListener(listener)
        }

        awaitClose { firebaseDatabase.removeEventListener(listener) }
    }




}