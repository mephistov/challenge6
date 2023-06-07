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
                            if(splitter[0] == userData.phone){
                                val tempData = childSnapshot.getValue(UserChatEntity::class.java)
                                tempData?.let {ue->
                                    listMessages.add(ue)
                                }

                            }
                            if(splitter[1] == userData.phone){
                                val tempData = childSnapshot.getValue(UserChatEntity::class.java)
                                tempData?.let {ue->
                                    listMessages.add(ue)
                                }
                            }
                        }

                    }
                    trySend(listMessages).isSuccess
                }

                    // Aquí puedes obtener la clave y/o el valor de cada hijo
                    // Extract the non-conversation fields
            /*        val imgProfile = childSnapshot.child("imgProfile").getValue(String::class.java) ?: ""
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
*/
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
                .child((it.phone+"_"+data.phone).orderToFirebaseDb())
                .child("messages")

            referConversation.addValueEventListener(listener)


        }
        awaitClose { firebaseDatabase.removeEventListener(listener) }
    }

    override fun sendMessage(message: Conversation) {
        referConversation.child(message.timestamp.toString()).setValue(message)
    }

}