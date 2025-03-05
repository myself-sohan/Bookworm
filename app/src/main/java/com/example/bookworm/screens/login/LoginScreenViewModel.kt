package com.example.bookworm.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookworm.model.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel() {
    //val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailAndPassword(
        email : String,
        password : String,
        home : () -> Unit
    )
    = viewModelScope.launch {
        try{

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful)
                    {
                        Log.d("FB", "signInWithEmailAndPassword: ${task.result}")
                        home()
                    }
                    else
                    {
                        Log.d("FB", "signInWithEmailAndPassword: ${task.result}")
                    }
                }
        }catch(ex: Exception){
            Log.d("FB", "signInWithEmailAndPassword: ${ex.message}")
        }
    }

    fun createUserWithEmailAndPassword(
        email : String,
        password : String,
        home : () -> Unit
    )
    {
        if(_loading.value == false)
        {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val displayName = task.result.user?.email?.split("@")[0]
                        createUser(displayName)
                        home()
                    } else
                        Log.d("FB", "createUserWithEmailAndPassword: ${task.result}")
                }
            _loading.value = false
        }
    }
    private fun createUser(string: String?)
    {
        val userId = auth.currentUser?.uid
        val user = MUser(
            id = null,
            userId = userId.toString(),
            displayName = string.toString(),
            avatarUrl = "",
            quote = "Life is great",
            profession = "Android Developer"
        ).toMap()
        user["user_id"] = userId.toString()
        user["display_name"] = string.toString()
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .add(user)
    }
}


