package com.nicolascastilla.challenge6.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolascastilla.domain.repositories.entities.contacts.ContactsEntity
import com.nicolascastilla.domain.repositories.usecases.interfaces.GetAutenticationUseCase
import com.nicolascastilla.domain.repositories.usecases.interfaces.GetInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewChatViewModel @Inject constructor(
    private val userUseCase: GetInfoUseCase
):ViewModel() {

    //val fcontactsList: Flow<List<ContactsEntity>> = emptyFlow()//userUseCase.getAllContacts()
    val contactsList = mutableStateOf<List<ContactsEntity>>(listOf())
    var fullList:List<ContactsEntity> = emptyList()
    val goToChat = MutableLiveData<HashMap<String,String>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val data = userUseCase.getAllContacts()
            data.collect{
                contactsList.value = it.sortedBy { it.name }
                fullList = contactsList.value
            }
        }
    }

    fun searchByText(text:String){
        if(text == ""){
            contactsList.value = fullList
        }else {
            var tempList = fullList
            tempList = tempList.filter {
                it.name.contains(text, ignoreCase = true) || it.phone.contains(
                    text,
                    ignoreCase = true
                )
            }
            contactsList.value =tempList
        }

    }

    fun cleanData(){
        for(item in contactsList.value){
            item.isChecked = false
        }
    }

    fun setChats() {
        var usesrsChat = "";
        var usesrsNames = "";
        for(item in contactsList.value){
            if(item.isChecked){
                Log.e("TEST", "ESTE si: ${item.name}")
                usesrsChat += item.phone+"_"
                usesrsNames += item.name+","
            }
        }
        usesrsChat = usesrsChat.substring(0, usesrsChat.length - 1);
        usesrsNames = usesrsNames.substring(0, usesrsNames.length - 1);
        val tempHasmap = HashMap<String,String>()
        tempHasmap.put("phone",usesrsChat)
        tempHasmap.put("name",usesrsNames)
        goToChat.value= tempHasmap
        cleanData()
    }
}