package com.example.bookworm.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.bookworm.R


@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Companion.Done,
    onAction: KeyboardActions = KeyboardActions.Companion.Default
)
{
    val visualTransformation =
        if(passwordVisibility.value)
            VisualTransformation.Companion.None
        else
            PasswordVisualTransformation()
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        },
        label = {
            Text(text = labelId)
        },
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = MaterialTheme.typography.labelMedium.fontSize
        ),
        singleLine = true,
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Companion.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        keyboardActions = onAction,
        trailingIcon = {
            PasswordVisibility(passwordVisibility = passwordVisibility)
        },

        )
}
@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visibility = passwordVisibility.value
    IconButton(onClick = {
        passwordVisibility.value = !visibility
    })
    {
        val painterResource = if(visibility)R.drawable.visibility_off else R.drawable.visibility
        val description = if (visibility) "Hide password" else "Show password"
        Icon(
            painter = painterResource(painterResource),
            contentDescription = description,
            modifier = Modifier.size(25.dp)
        )
    }
}