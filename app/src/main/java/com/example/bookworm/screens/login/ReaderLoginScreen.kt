package com.example.bookworm.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bookworm.components.EmailInput
import com.example.bookworm.components.PasswordInput
import com.example.bookworm.components.ReaderLogo
import com.example.bookworm.R
import com.example.bookworm.navigation.ReaderScreens

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = viewModel()
)
{
    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(top = 50.dp)
            )
            {
                ReaderLogo()
                if(showLoginForm.value)
                {
                    UserForm(
                        loading = false,
                        isCreateAccount = false,
                    )
                    {
                            email, pwd ->
                        viewModel.signInWithEmailAndPassword(
                            email =email,
                            password = pwd
                        )
                        {
                            navController.navigate(ReaderScreens.HomeScreen.name)

                        }
                    }
                }
                else
                {
                    UserForm(
                        loading = false,
                        isCreateAccount = true
                    )
                    {
                            email, pwd ->
                        viewModel.createUserWithEmailAndPassword(
                            email =email,
                            password = pwd
                        )
                        {
                            navController.navigate(ReaderScreens.HomeScreen.name)

                        }
                    }
                }
            }
            Spacer(
                modifier = Modifier.height(15.dp)
            )
            Row(
                modifier = Modifier.padding(15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                val text = if(showLoginForm.value) "Sign up" else "Login"
                val message = if(showLoginForm.value) "New User" else "Already have an account?"
                Text(
                    text = message
                )
                Text(
                    text = text,
                    modifier = Modifier.clickable{
                        showLoginForm.value = !showLoginForm.value
                    }
                        .padding(start = 5.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
@Preview
@Composable
fun UserForm(
    loading : Boolean = false,
    isCreateAccount : Boolean = false,
    onDone : (String, String) -> Unit = {email, pwd ->}
)
{
    val context = LocalContext.current
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value)
    {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val modifier = Modifier
        .height(280.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        if(isCreateAccount)
        {
            Text(
                text = stringResource(id = R.string.create_acc),
                modifier = Modifier.padding(14.dp)
            )
        }
        else
            Text("")
        EmailInput(
            emailState = email,
            enabled = !loading,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            },
        )
        PasswordInput(
            modifier = Modifier
                .focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Password",
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) {
                    Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                    return@KeyboardActions
                }
            }
        )
        SubmitButton(
            textId =
            if (isCreateAccount)
                "Create Account"
            else
                "Login",
            loading = loading,
            validInputs = valid
        )
        {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()

        }
    }
}

@Composable
fun SubmitButton(
    textId: String,
    loading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit = {}
)
{
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape
    )
    {
        if(loading)
            CircularProgressIndicator(
                modifier = Modifier
                    .size(25.dp)
            )
        else
            Text(
                text = textId,
                modifier = Modifier
                    .padding(5.dp)
            )
    }
}



