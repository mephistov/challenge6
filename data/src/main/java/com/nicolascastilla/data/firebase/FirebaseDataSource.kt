package com.nicolascastilla.data.firebase

import com.nicolascastilla.domain.repositories.entities.MessageItem
import kotlinx.coroutines.flow.Flow

interface FirebaseDataSource {
     fun getItems(iduser:String): Flow<List<MessageItem>>
     fun addItem(item: MessageItem): Flow<Result<Unit>>
     fun removeItem(itemId: String): Flow<Result<Unit>>
}