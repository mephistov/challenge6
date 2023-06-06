package com.nicolascastilla.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.nicolascastilla.data.local.preferences.UserPreferences
import com.nicolascastilla.domain.repositories.FirebaseAutenticationRepository
import com.nicolascastilla.domain.repositories.entities.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class FirebaseAutentication @Inject constructor(
    val aut: FirebaseAuth,
    private val firebaseDatabase: DatabaseReference,
    private val shareData: UserPreferences
): FirebaseAutenticationRepository {




    override suspend fun isUserAutenticated(user: UserData): Flow<UserData?> = callbackFlow {
        val authResultTask = aut.signInWithEmailAndPassword(user.emai,user.password)
                authResultTask.addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val userF = aut.currentUser
                        //TODO get user from firebase uid ref
                        val userData = UserData(
                            systemId = "",
                            name = "",
                            emai = user.emai,
                            password = user.password,
                            phone= "",
                            uid = task.result.user?.uid!!
                        )
                        CoroutineScope(Dispatchers.IO).launch {
                            shareData.setUser(userData)
                        }

                        trySend(userData).isSuccess
                    }
                    else {
                        trySend(null).isSuccess
                        close()
                    }
                }



        awaitClose { }

    }

    override suspend fun createUser(user: UserData): Flow<UserData?> = callbackFlow {
        aut.createUserWithEmailAndPassword(user.emai,user.password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val myRef = firebaseDatabase.child("users").child(task.result.user?.uid!!)
                    user.uid = task.result.user?.uid!!
                    myRef.setValue(user)
                    CoroutineScope(Dispatchers.IO).launch {
                        shareData.setUser(user)
                    }
                    trySend(user).isSuccess
                }
                else {
                    trySend(null).isSuccess
                    close()
                }
            }
        awaitClose { }

    }

    override suspend fun isUserLogged(): Flow<UserData?> {
        return  shareData.user
    }
}