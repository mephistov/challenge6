package com.nicolascastilla.challenge6.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolascastilla.domain.repositories.entities.UserData
import com.nicolascastilla.domain.repositories.usecases.interfaces.GetAutenticationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterUserDataViewModel @Inject constructor(
    private val userUseCase:GetAutenticationUseCase
):ViewModel() {


    val isRegisterUser = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val isSplashScreen = mutableStateOf(true)
    val email = mutableStateOf("")
    val phone = mutableStateOf("")
    val password = mutableStateOf("")
    val name = mutableStateOf("")
    var userData:UserData? = null
    val isValidated = MutableLiveData<Boolean>()


    init {

    }


    fun validateUser() {
        val userData = UserData(
            systemId = "",
            name = name.value,
            emai = email.value,
            password = password.value,
            phone=phone.value,
            uid = ""
        )
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {

                val tempUseD = userUseCase.createuser(userData)
                tempUseD.collect{createData->
                    if(createData != null){
                        isRegisterUser.value = true
                        isValidated.postValue(true)
                    }
                }


            isLoading.value = false

        }
        //TODO tener aqui estados de succes fail?? mostar errores que llegan de firebase

    }

    fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    fun isValidPhoneNumber(phone: String): Boolean {
        // Aquí puedes añadir tu propio patrón para números de teléfono
        val phonePattern = "^[+]?[0-9]{10,13}$"
        return phone.matches(phonePattern.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

}