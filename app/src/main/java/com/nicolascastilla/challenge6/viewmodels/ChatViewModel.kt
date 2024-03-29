package com.nicolascastilla.challenge6.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolascastilla.challenge6.utils.GlobalInfo
import com.nicolascastilla.domain.repositories.entities.messages.Conversation
import com.nicolascastilla.domain.repositories.entities.messages.UserChatEntity
import com.nicolascastilla.domain.repositories.usecases.interfaces.GetChatUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCase: GetChatUseCases
):ViewModel() {

    //var chatList: Flow<List<Conversation>> = emptyFlow()
    val chatList = mutableStateOf<List<Conversation>>(listOf())

    init {
        /*viewModelScope.launch(Dispatchers.IO) {

        }*/

    }

    fun setupFirstChat(name: String, phone: String, idChat: String){

        viewModelScope.launch(Dispatchers.IO) {
            val data = UserChatEntity(
                name = name,
                imgProfile = "",
                phone = phone,
                timestamp = Date().time,
                lastMessage = "",
                idChat = idChat
                //messages = mutableListOf()
            )
            val TchatList = chatUseCase.getAllConversation(data)
            TchatList.collect{
                if(it.size == 0)
                    chatUseCase.setupDataBase(data)
                chatList.value = it

            }

        }


    }

    fun sendMessage(name:String, phone:String, messa: String){

        val message = GlobalInfo.globalUserData?.let {
            Conversation(
                idUser = it.phone,
                image = "",
                message = messa,
                name = name,
                timestamp = Date().time
            )

        }
        viewModelScope.launch() {
            chatUseCase.sendMessage(message!!)
        }

    }


}