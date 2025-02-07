package com.example.experiment.random

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.experiment.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VoiceToTextParser(
    private val app : Application
) : RecognitionListener {
    private val _state = MutableStateFlow(VoiceToTextParserState( ))
    val state =  _state.asStateFlow()
    val recognizer = SpeechRecognizer.createSpeechRecognizer(app )

    fun startListening(languageCode : String = "en") {
        _state.update { VoiceToTextParserState() }
        if (!SpeechRecognizer.isRecognitionAvailable(app)){
             _state.update {
                 it.copy(
                     error = "The recognition is not available "
                 )
             }
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
             putExtra(
                 RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                 RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
             )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
        }
        recognizer.setRecognitionListener(this)
        recognizer.startListening(intent)
        _state.update {
            it.copy(
                isSpeaking = true
            )
        }
    }
    fun stopListening() {
        _state.update {
            it.copy(
                isSpeaking = false
            )
        }
        recognizer.stopListening()
    }

    override fun onReadyForSpeech(p0: Bundle?) {
        _state.update {
            it.copy(
                error = null
            )
        }
    }

    override fun onBeginningOfSpeech() {
        _state.update {
            it.copy(
                error = "Hearing..."
            )
        }
    }

    override fun onRmsChanged(rms: Float) {
        _state.update {
            it.copy(
                rmsValue = rms
            )
        }
    }

    override fun onBufferReceived(p0: ByteArray?) = Unit

    override fun onEndOfSpeech() {
        _state.update {
            it.copy(
                isSpeaking = false
            )
        }
    }

    override fun onError(error: Int) {
        if (error == SpeechRecognizer.ERROR_CLIENT ) return
        _state.update {
            it.copy(
                error = "Error = $error "
            )
        }
    }

    override fun onResults(results: Bundle?) {
        results
            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let {result->
                 _state.update {
                     it.copy(
                          spokenText = result
                     )
                 }
            }
    }

    override fun onPartialResults(partialResult: Bundle?) {
        partialResult
            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { result->
                _state.update {
                    it.copy(
                        spokenText = result
                    )
                }
            }
    }
    override fun onEvent(p0: Int, p1: Bundle?) = Unit
}

data class VoiceToTextParserState(
    val spokenText : String = "",
    val isSpeaking : Boolean = false,
    val error : String? = "",
    val rmsValue : Float = 0.0f
)


//Box(
//modifier = Modifier
//.fillMaxWidth()
//.wrapContentHeight()
//) {
//    if (trainDays != 0) {
//        Canvas(
//            modifier = Modifier.matchParentSize()
//        ) {
//            val totalSpacingPx = daySpacing.toPx() * 6
//            val rowWidthPx = size.width
//            val dayWidthPx = (rowWidthPx - totalSpacingPx) / 7
//            val tintedWidth = (trainDays * dayWidthPx) + ((trainDays - 1) * daySpacing.toPx())
//
//            val expandPx = 8.dp.toPx()
//
//            // We offset the shape's left edge a bit to the left
//            val leftX = -expandPx.coerceAtMost(size.width)
//            val shapeWidth = tintedWidth + 2 * expandPx
//            val cornerRadius = size.height / 2f
//            drawRoundRect(
//                color = Color(0xFFFFF2E7),
//                topLeft = Offset(x = -18f, y = 0f),
//                size = Size(width = shapeWidth.coerceAtMost(size.width - leftX), height = size.height),
//                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
//            )
//        }
//    }
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .align(Alignment.Center)
//            .padding()
//            .defaultMinSize(minHeight = 40.dp)
//            .wrapContentHeight(),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.spacedBy(daySpacing)
//    ) {
//        weekDates.forEachIndexed { index, date ->
//            val dateNumber = date.dayOfMonth.toString()
//            val isToday = (date == today)
//
//            Box(
//                modifier = Modifier.weight(1f),
//                contentAlignment = Alignment.Center
//            ) {
//                if (trainDays > index) {
//                    Image(
//                        modifier = Modifier.padding(vertical = 6.dp).padding(end = if (index == 7) 16.dp else 0.dp),
//                        painter = painterResource(R.drawable.streak_component),
//                        contentDescription = ""
//                    )
//                }
//                else {
//                    Text(
//                        text = dateNumber,
//                        fontSize = 16.sp,
//                        textAlign = TextAlign.Center,
//                        color = if (isToday) Color(0xFFFF8A00) else Color.Gray
//                    )
//                }
//            }
//        }
//    }
//}
//}
//}