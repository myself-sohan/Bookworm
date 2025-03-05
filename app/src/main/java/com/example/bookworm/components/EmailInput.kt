package com.example.bookworm.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun EmailInput(
    modifier: Modifier = Modifier.Companion,
    emailState: MutableState<String>,
    labelId : String = "Email",
    enabled: Boolean,
    imeAction: ImeAction = ImeAction.Companion.Next,
    onAction: KeyboardActions = KeyboardActions.Companion.Default

)
{
    InputField(
        modifier = modifier,
        valueState = emailState,
        enabled = enabled,
        labelId = labelId,
        keyboardType = KeyboardType.Companion.Email,
        imeAction = imeAction,
        onAction = onAction
    )
}
