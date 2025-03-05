package com.example.bookworm.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bookworm.R
import com.example.bookworm.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderAppBar(
    title: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    navController: NavController,
    onBackArrowClicked: () -> Unit = {}
)
{
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.Companion.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.Companion.fillMaxWidth(0.7f)
            ) {
                if (showProfile) {
                    Surface(
                        modifier = Modifier.Companion
                            .padding(5.dp)
                            .size(30.dp),
                    )
                    {
                        Image(
                            painter = painterResource(R.mipmap.book_launcher_foreground),
                            contentDescription = "Toast Icon",
                            contentScale = ContentScale.Companion.Fit,
                            modifier = Modifier.Companion
                                .clip(RoundedCornerShape(5.dp))
                                .scale(1.1f)
                        )
                    }
                }
                if (icon != null)
                {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Arrow Back Icon",
                        tint = Color.Companion.Red.copy(alpha = 0.7f),
                        modifier = Modifier.clickable{
                            onBackArrowClicked.invoke()
                        }
                    )
                }
                Text(
                    text = title,
                    color = Color.Companion.Red.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily(
                            Font(R.font.rubik_gemstones)
                        ),
                        fontWeight = FontWeight.Companion.Bold,
                    )
                )
                //Spacer(modifier = Modifier.width(15.dp))
            }
        },
        actions = @Composable {
            if(showProfile)
            {
                IconButton(
                    onClick = {
                        FirebaseAuth.getInstance().signOut().run {
                            navController.navigate(ReaderScreens.LoginScreen.name)
                        }
                    }
                )
                {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.Logout,
                        contentDescription = "Logout Icon"
                    )
                }
            }
            else
                Box{}
            },
        colors = TopAppBarDefaults.topAppBarColors(Color.Companion.LightGray)
    )
}