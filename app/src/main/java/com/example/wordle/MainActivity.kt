package com.example.wordle

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.wordle.ui.theme.WordleTheme

class MainActivity : ComponentActivity() {
    val setOfWords = setOf<String>("apple", "laser", "panda")
    val targetWord = setOfWords.random()
    // variable like "guess" that says correct, wrong position, etc
    // Log.d("logdlauren", "Target word is $targetWord")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Wordle(targetWord)
                }
            }
        }
    }
    enum class GuessResult {
        CORRECT, WRONG_POSITION, NOT_IN_WORD
    }
}



@Composable
fun Wordle(targetWord: String) {
    var guess by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = guess,
            onValueChange = { newText ->
                guess = newText
            },
            label = {Text("Enter your guess here.")}
        )

        val results = mutableListOf<MainActivity.GuessResult>()

        // Loop through indexes of guess
        // Check if the letters in both words (guess and targetWord) match
        // Check if guess letter is anywhere in targetWord

        if (guess.length == 5) {
            Log.d("logdlauren", "Guess length is 5")
            for (guessIndex in 0 until guess.length) {
                var result = MainActivity.GuessResult.NOT_IN_WORD
                Log.d("logalauren", "${guess[guessIndex]}")
                if (guess[guessIndex] == targetWord[guessIndex]) {
                    result = MainActivity.GuessResult.CORRECT
                    Log.d("logblauren", "${guess[guessIndex]} is in the correct position")
                } else if (guess[guessIndex] !== targetWord[guessIndex]) {
                    for (targetIndex in 0 until targetWord.length) {
                        if (guess[guessIndex] == targetWord[targetIndex]) {
                            result = MainActivity.GuessResult.WRONG_POSITION
                            Log.d("logclauren", "${guess[guessIndex]} is in the word, but in the wrong position")
                            break
                        }
                    }
                }
                results.add(result)
            }
            Log.d("logelauren", "$results")
            Row () {
                for (guessIndex in 0 until guess.length) {
                    var letter = guess[guessIndex]
                    var result = results[guessIndex]
                    var color = Color.DarkGray

                    if (result == MainActivity.GuessResult.CORRECT) {
                        color = Color.Green
                    } else if (result == MainActivity.GuessResult.WRONG_POSITION) {
                        color = Color.Yellow
                    } else {
                        color = Color.DarkGray
                    }
                    Text("$letter",
                        color = color,
                        fontSize = 50.sp)
                }
            }
        }
    }
}



@Preview
@Composable
fun WordlePreview() {
    Wordle("apple")
}


