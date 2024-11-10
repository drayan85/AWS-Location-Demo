package com.aws.demo.home

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.aws.demo.ui.theme.AWSDemoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AWSDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        lifecycleScope.launch {
            viewModel.effects.collect(::handleEffects)
        }

        viewModel.getAddressSuggestions("Wentworthville")

        lifecycleScope.launch {
            delay(5000)
            viewModel.getAddressSuggestions("Parramatta")
        }
    }

    private fun handleEffects(effect: MainViewModelEffect) {
        when (effect) {
            is MainViewModelEffect.DisplayError -> {
                effect.error.printStackTrace()
                Log.d("AWS", "handleEffects: ${effect.error}")
            }
            is MainViewModelEffect.DisplaySuggestions -> {
                effect.suggestions.forEach {
                    Log.d("AWS", "handleEffects: $it")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AWSDemoTheme {
        Greeting("Android")
    }
}