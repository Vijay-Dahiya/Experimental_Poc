package com.example.experiment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.experiment.random.CalendarDay
import com.example.experiment.random.StreakFlameTypesText
import com.example.experiment.random.Subtitle
import com.example.experiment.random.Title
import com.example.experiment.random.VoiceToTextParser
import com.example.experiment.random.getCurrentWeekDates
import com.example.experiment.random.isBeforeOrEquals
import com.example.experiment.random.isLastDayOfWeek
import com.google.android.material.transition.MaterialContainerTransform.FitMode
import kotlinx.coroutines.delay
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

    private val voiceToTextParser by lazy {
        VoiceToTextParser(this.application)
    }
    val trainColor = Color(0xFFFF820E).copy(alpha = 0.1f)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    StreakStoryScreenDone(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun StreakStoryScreenDone(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(
                    color = Color.Gray,
                    shape = RoundedCornerShape(12.dp),
                    width = 3.dp
                )
        ) {
            CurrentWeekCalendarDoneTwoRows(
                modifier = Modifier.fillMaxWidth(), 5
            )
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun CurrentWeekCalendarDoneTwoRows(
        modifier: Modifier = Modifier,
        trainDays: Int = 3
    ) {
        val weekDates = remember { getCurrentWeekDates() }
        val today = LocalDate.now()

        val daySpacing = 8.dp
        val sizeIncrement = if(trainDays==7)0.dp else 6.dp
        Column(
            modifier = modifier
                .padding(vertical = 14.dp)
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(daySpacing)
            ) {
                weekDates.forEach { date ->
                    val dayLetter = date.dayOfWeek.name.take(1).uppercase()
                    val isToday = (date == today)

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = dayLetter,
                            fontSize = 12.sp,
                            color = if (isToday) Color(0xFFFF8A00) else Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .defaultMinSize(minHeight = 40.dp)
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(daySpacing)
                ) {
                    weekDates.forEachIndexed { index, date ->
                        val dateNumber = date.dayOfMonth.toString()
                        val isToday = (date == today)

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(

                                    if (index == 0) {
                                        RoundedCornerShape(
                                            topStart = 15.dp,
                                            bottomStart = 15.dp,
                                            topEnd = 0.dp,
                                            bottomEnd = 0.dp
                                        )
                                    } else if (index == trainDays-1) {
                                        RoundedCornerShape(
                                            topStart = 0.dp,
                                            bottomStart = 0.dp,
                                            topEnd = 15.dp,
                                            bottomEnd = 15.dp
                                        )
                                    }
                                    else {
                                       RoundedCornerShape(0.dp)
                                    }
                                )
                                .background(if (index in 0..<trainDays) trainColor else Color.Transparent)
                            ,
                            contentAlignment = Alignment.Center
                        ) {
                            if (trainDays > index) {
                                Image(
                                    modifier = Modifier.padding(vertical = 6.dp),
                                    painter = painterResource(R.drawable.streak_component),
                                    contentDescription = ""
                                )
                            }
                            else {
                                Text(
                                    text = dateNumber,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    color = if (isToday) Color(0xFFFF8A00) else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun CurrentWeekCalendarDone(
        modifier: Modifier = Modifier
    ) {
        val weekDates = remember { getCurrentWeekDates() }
        val today = LocalDate.now()
        val dayFormatter = DateTimeFormatter.ofPattern("E")
        val dateFormatter = DateTimeFormatter.ofPattern("d")
        Row(
            modifier = modifier
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
                .padding(top = 14.dp, bottom = 24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            weekDates.forEach { date ->
                val dayLetter = date.format(dayFormatter).take(1).uppercase()
                val dateNumber: String = date.format(dateFormatter)

                val isToday = (date == today)

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 6.dp),
                        text = dayLetter,
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (isToday) Color(0xFFFF8A00) else Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = dateNumber,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = if (isToday) Color(0xFFFF8A00) else Color.Gray
                        )
                    )
                }
            }
        }
    }

    /**
     * Returns a list of [startIndex, endIndex] pairs for consecutive non-zero runs in [days].
     * Example: [1,2,2,0,0,3,3] => runs = [(0,2), (5,6)]
     */
    fun findConsecutiveNonZeroRuns(days: List<Int>): List<Pair<Int, Int>> {
        val runs = mutableListOf<Pair<Int, Int>>()
        var runStart = -1

        for (i in days.indices) {
            if (days[i] != 0) {
                // If we're not already in a run, start one
                if (runStart == -1) runStart = i
            } else {
                // We've hit a zero => if we were in a run, close it
                if (runStart != -1) {
                    runs.add(runStart to (i - 1))
                    runStart = -1
                }
            }
        }
        // If days ended with a run open, close it
        if (runStart != -1) {
            runs.add(runStart to (days.lastIndex))
        }
        return runs
    }

    @Composable
    fun StreakStoryScreen() {
        val testData = StreakFlameTypesText(
            title = Title(
                baseText = "Supernova Streak!!",
                flipText = "You've gone nuclear!"
            ),
            subtitle = Subtitle(
                baseText = "You have reached the hottest streak.",
                flipText = "This is the limit!"
            )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            FlipText(streakFlameTypesText = testData)
        }
    }

    @Composable
    fun FlipText(streakFlameTypesText: StreakFlameTypesText?) {
        if (streakFlameTypesText == null) return

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.wrapContentSize()
        ) {
            // 1) Large Title Flip
            FlipAnimatedText(
                baseText = streakFlameTypesText.title?.baseText ?: "",
                flipText = streakFlameTypesText.title?.flipText ?: "",
                baseTextStyle = MaterialTheme.typography.headlineLarge,  // For example, big style
                flipTextStyle = MaterialTheme.typography.headlineLarge,
                flipDelayMillis = 2000L,
                animationDurationMillis = 600
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 2) Smaller Subtitle Flip
            FlipAnimatedText(
                baseText = streakFlameTypesText.subtitle?.baseText ?: "",
                flipText = streakFlameTypesText.subtitle?.flipText ?: "",
                baseTextStyle = MaterialTheme.typography.bodyMedium,  // smaller style
                flipTextStyle = MaterialTheme.typography.bodyMedium,
                flipDelayMillis = 2000L,
                animationDurationMillis = 600
            )
        }
    }

    @Composable
    fun FlipAnimatedText(
        baseText: String,
        flipText: String,
        baseTextStyle: TextStyle,
        flipTextStyle: TextStyle,
        flipDelayMillis: Long = 3000,
        animationDurationMillis: Int = 600
    ) {
        var isFlipped by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(flipDelayMillis)
            isFlipped = true
        }

        val rotation by animateFloatAsState(
            targetValue = if (isFlipped) 180f else 0f,
            animationSpec = tween(durationMillis = animationDurationMillis)
        )

        val currentText = if (rotation <= 90f) baseText else flipText
        val currentStyle = if (rotation <= 90f) baseTextStyle else flipTextStyle

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .graphicsLayer {
                    rotationX = rotation
                    cameraDistance = 8 * density
                }
        ) {
            if (rotation > 90f) {
                Box(modifier = Modifier.graphicsLayer { rotationX = 180f }) {
                    Text(text = currentText, style = currentStyle)
                }
            } else {
                Text(text = currentText, style = currentStyle)
            }
        }
    }


}



