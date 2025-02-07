package com.example.experiment.random

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.experiment.R
import kotlinx.coroutines.delay

@Composable
fun RecapScreen(
    displayTimeMillis: Long, // Time to display the screen in milliseconds
    onCancel: () -> Unit,    // Callback to cancel the screen
    shareImage: () -> Unit   // Callback for the "Share with friends" button
) {
    // Remember coroutine scope
    val scope = rememberCoroutineScope()

    // Start a timer to cancel the screen
    LaunchedEffect(Unit) {
        delay(displayTimeMillis)
        onCancel()
    }

    // Main UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE6F7FF)), // Light background color
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Close Button
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopStart
            ) {
                IconButton(onClick = { onCancel() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Black
                    )
                }
            }

            // Content
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Recap",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "You studied for",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

                Text(
                    text = "2,583 minutes",
                    style = TextStyle(
                        color = Color(0xFF1E90FF), // Blue text color
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = "minutes.",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "You were among the top 15% of learners last year.",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your cat image resource
                    contentDescription = "Cat",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(16.dp)
                )
            }

            // Share Button
            Button(
                onClick = { shareImage() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF1E90FF), // Blue button color
                )
            ) {
                Text(
                    text = "Share with friends",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}
