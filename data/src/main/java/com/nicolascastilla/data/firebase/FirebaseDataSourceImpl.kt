package com.nicolascastilla.data.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.nicolascastilla.domain.repositories.entities.MessageItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.flow


class FirebaseDataSourceImpl(private val dbReference: DatabaseReference) : FirebaseDataSource {

    override fun getItems(iduser:String): Flow<List<MessageItem>> = callbackFlow  {
        val eventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val items = dataSnapshot.children.mapNotNull { it.getValue(MessageItem::class.java) }
                trySend(items).isSuccess // Emit the items
                //emit(items)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error here
            }
        }

        dbReference.child("chats").child(iduser).addValueEventListener(eventListener)

        // Remove the listener when the Flow collector is cancelled
        awaitClose { dbReference.removeEventListener(eventListener) }
    }

    override fun addItem(item: MessageItem): Flow<Result<Unit>> = flow {
        try {
            dbReference.child(item.id.toString()).setValue(item).await()
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun removeItem(itemId: String): Flow<Result<Unit>> = flow {
        try {
            dbReference.child(itemId).removeValue().await()
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}