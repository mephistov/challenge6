package com.nicolascastilla.challenge6.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nicolascastilla.domain.repositories.entities.MessageItem
import com.nicolascastilla.domain.repositories.entities.messages.UserChatEntity
import com.nicolascastilla.domain.repositories.usecases.interfaces.GetInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getInfoUseCase: GetInfoUseCase
):ViewModel() {

    val goToChat = MutableLiveData<HashMap<String,String>>()

    fun setChatView(item: UserChatEntity) {
        val tempHasmap = HashMap<String,String>()
        tempHasmap.put("phone",item.phone)
        tempHasmap.put("name",item.name)
        goToChat.value= tempHasmap
    }

    val myItems: Flow<List<UserChatEntity>> = getInfoUseCase.getAllUserInfo()

    init {

    }



}