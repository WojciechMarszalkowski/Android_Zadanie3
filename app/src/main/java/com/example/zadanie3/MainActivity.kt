package com.example.zadanie3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import com.example.zadanie3.ui.theme.RotatingTriangleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RotatingTriangleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RotatingTriangleScreen()
                }
            }
        }
    }
}

@Composable
fun RotatingTriangleScreen() {
    // Zmienna przechowująca stan rotacji
    var isRotating by remember { mutableStateOf(false) }

    // Animacja rotacji
    val rotationAngle by animateFloatAsState(
        targetValue = if (isRotating) 360f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Obsługa dotknięcia ekranu i uruchamianie animacji
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        isRotating = !isRotating
                    }
                )
            }
    ) {
        RotatingTriangle(rotationAngle = rotationAngle)
    }
}

@Composable
fun RotatingTriangle(rotationAngle: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Środek ekranu
        val canvasWidth = size.width
        val canvasHeight = size.height
        val centerX = canvasWidth / 2
        val centerY = canvasHeight / 2

        // Wielkość trójkąta
        val triangleSize = 200f

        // Ścieżka do narysowania trójkąta
        val trianglePath = Path().apply {
            moveTo(centerX, centerY - triangleSize / 2) // Górny wierzchołek trójkąta
            lineTo(centerX - triangleSize / 2, centerY + triangleSize / 2) // Lewy dolny wierzchołek
            lineTo(centerX + triangleSize / 2, centerY + triangleSize / 2) // Prawy dolny wierzchołek
            close() // Zamknij ścieżkę
        }

        // Obracanie trójkąta wokół środka
        rotate(degrees = rotationAngle, pivot = Offset(centerX, centerY)) {
            drawPath(path = trianglePath, color = Color.Blue)
        }
    }
}
