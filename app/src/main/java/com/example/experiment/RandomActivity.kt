package com.example.experiment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.experiment.ui.theme.ExperimentTheme
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.asCompletableFuture
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RandomActivity : ComponentActivity() {
    private val TAG = "Vijay"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random)
        callExperiment()
//        callExperiment2()
        callExperiment4()

        setContent {
            // Compose UI
            ExperimentTheme {

            }
        }

    }

    suspend fun  callOne() {
        delay(2000)
    }
    suspend fun  callTwo() {
        delay(3000)
    }

    private fun callExperiment() {
        val start = System.currentTimeMillis()
        Log.d(TAG, "callExperiment: Start")
        lifecycleScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) {
                callOne()
            }
            withContext(Dispatchers.Default) {
                callTwo()
            }

            val end = System.currentTimeMillis()
            val elapsedSeconds = (end - start) / 1000.0
            Log.d("Timing1", "Block took $elapsedSeconds seconds to complete.")
        }
        Log.d(TAG, "callExperiment: End")

    }

    private fun callExperiment2() {
        val start = System.currentTimeMillis()
        Log.d(TAG, "callExperiment2: Start")
        val job1 = lifecycleScope.launch {
            Log.d(TAG, "callExperiment2: In 1")
            callOne()
            val end = System.currentTimeMillis()
            val elapsedSeconds = (end - start) / 1000.0
            Log.d("Timing2 first", "Block took $elapsedSeconds seconds to complete.")
        }

        lifecycleScope.launch {
            Log.d(TAG, "callExperiment2: In 2")
            job1.join()
            callTwo()
            val end = System.currentTimeMillis()
            val elapsedSeconds = (end - start) / 1000.0
            Log.d("Timing2 Second", "Block took $elapsedSeconds seconds to complete.")
        }

        lifecycleScope.launch {
            callTwo()
            val end = System.currentTimeMillis()
            val elapsedSeconds = (end - start) / 1000.0
            Log.d("Timing2", "Block took $elapsedSeconds seconds to complete.")
        }
        val end = System.currentTimeMillis()
        val elapsedSecondsT = (end - start) / 1000.0
        Log.d("Timing2 Total", "Block took $elapsedSecondsT seconds to complete.")
        Log.d(TAG, "callExperiment2: End")
    }

    suspend fun callExperiment3(): Deferred<names> {
        val a = lifecycleScope.async {
            delay(1000)
            names(first = "Vijay", null)
        }
        val b = lifecycleScope.async {
            delay(1000)
            a.await().second = " is a god boy"
            return@async a
        }
        return b.await()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun callExperiment4() {
        var value : names? = null
        lifecycleScope.launch {
            value = callExperiment3().getCompleted()
            Log.d(TAG, "callExperiment4: $value")
        }
    }

    data class names(val first : String?, var second : String?)
}