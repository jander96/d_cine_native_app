package com.devj.dcine

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devj.dcine.core.data.Api
import com.devj.dcine.core.data.dtos.movie.MovieDto
import com.devj.dcine.ui.theme.DCineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DCineTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val movies = remember { mutableStateListOf<MovieDto>() }

    LaunchedEffect(Unit) {
        try {
            val api = Api.create().getPopularMovies()
            val response = api.results
            movies.clear()
            movies.addAll(response)
        } catch (e: Exception) {
            Log.e("Greeting", "Error fetching movies: ${e.message}")
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Hello, $name!", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        LazyColumn {
            items(movies) { movie ->
                Text(text = movie.title, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DCineTheme {
        Greeting("Android")
    }
}