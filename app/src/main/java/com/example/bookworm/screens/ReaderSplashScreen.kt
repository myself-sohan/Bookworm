package com.example.bookworm.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bookworm.components.ReaderLogo
import com.example.bookworm.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
)
{
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(
        key1 = true,
    )
    {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 900,
                easing = {
                    OvershootInterpolator(7f)
                        .getInterpolation(it)
                }
            )
        )
        delay(2000L)
        if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
            navController.navigate(ReaderScreens.LoginScreen.name)
        else
            navController.navigate(ReaderScreens.HomeScreen.name)

    }
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp)
            .scale(scale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(
            width = 2.dp,
            color = Color.LightGray
        )
    )
    {
        Column(
            modifier = Modifier
                .padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {


            ReaderLogo()
            Spacer(
                modifier = Modifier.height(25.dp)
            )
            Text(
                text = "\"Read -> Change Yourself\"",
                style = MaterialTheme.typography.titleLarge,
                color = Color.LightGray
            )
        }
    }
}
