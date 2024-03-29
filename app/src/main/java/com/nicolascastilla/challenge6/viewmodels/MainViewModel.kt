package com.nicolascastilla.challenge6.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nicolascastilla.challenge6.ui.theme.BlueGradient
import com.nicolascastilla.challenge6.ui.theme.StrokeColor
import com.nicolascastilla.domain.repositories.entities.MessageItem
import com.nicolascastilla.domain.repositories.entities.messages.UserChatEntity
import com.nicolascastilla.domain.repositories.usecases.interfaces.GetAutenticationUseCase
import com.nicolascastilla.domain.repositories.usecases.interfaces.GetInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getInfoUseCase: GetInfoUseCase,
    private val userUseCase: GetAutenticationUseCase
):ViewModel() {

    val goToChat = MutableLiveData<HashMap<String,String>>()
    val selectedChatColor =  mutableStateOf(BlueGradient)
    val selectedProfileColor =  mutableStateOf(StrokeColor)

    fun setChatView(item: UserChatEntity) {
        val tempHasmap = HashMap<String,String>()
        tempHasmap.put("phone",item.phone)
        tempHasmap.put("name",item.name)
        tempHasmap.put("idChat",item.idChat)
        goToChat.value= tempHasmap
    }

    val nameProfile =  mutableStateOf<String>("")
    val emailProfile =  mutableStateOf("")
    val phoneProfile =  mutableStateOf("")
    fun gerUserInfo(){
        viewModelScope.launch {
            val data = userUseCase.isUserLogged()
            val user = data.first()
            user?.let {
                nameProfile.value = it.name
                emailProfile.value = it.emai
                phoneProfile.value = it.phone
            }


        }
    }

    val myItems: Flow<List<UserChatEntity>> = getInfoUseCase.getAllUserInfo()

    init {

    }



}