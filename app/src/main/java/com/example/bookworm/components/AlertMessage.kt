package com.example.bookworm.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertMessage(
    showDialog: MutableState<Boolean>,
    labelText: String,
    leftButtonText : String,
    rightButtonText : String,
    onLeftButtonClick : () -> Unit,
    onRightButtonClick: () -> Unit = {showDialog.value = false},
    modifier: Modifier = Modifier.Companion
)
{
    BasicAlertDialog(
        onDismissRequest = { showDialog.value = false },
    )
    {
        Card(
            modifier = Modifier.Companion
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(Color(62, 118, 213, 255)),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.Companion.padding(10.dp),
            )
            {
                Row(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Companion.CenterVertically
                ) {
                    Text(
                        text = labelText,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Companion.ExtraBold,
                        textAlign = TextAlign.Companion.Center,
                        color = Color.Companion.White
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Companion.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    NoteButton(
                        //modifier = Modifier.Companion.weight(1f),
                        text = leftButtonText,
                        onClick = onLeftButtonClick,
                        colors = ButtonDefaults.buttonColors(Color.Companion.Red.copy(0.6f))
                    )
//                    Spacer(
//                        modifier = Modifier.Companion
//                            .fillMaxWidth()
//                            .weight(1f)
//                    )
                    NoteButton(
                        //modifier = Modifier.Companion.weight(1f),
                        text = rightButtonText,
                        colors = ButtonDefaults.buttonColors(Color.Companion.Red.copy(0.6f)),
                        onClick = onRightButtonClick,
                    )
                }


            }
        }
    }
}
@Composable
fun NoteButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    colors: ButtonColors  = ButtonDefaults.buttonColors(),
    onClick: () -> Unit,
)
{
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors
    )
    {
        Text(text = text,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
    }
}