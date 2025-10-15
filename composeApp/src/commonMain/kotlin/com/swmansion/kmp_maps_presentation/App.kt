package com.swmansion.kmp_maps_presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.swmansion.kmpmaps.CameraPosition
import com.swmansion.kmpmaps.Circle
import com.swmansion.kmpmaps.Coordinates
import com.swmansion.kmpmaps.Map
import com.swmansion.kmpmaps.Marker
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    Map(
        modifier = Modifier.fillMaxSize(),
        cameraPosition = CameraPosition(
            coordinates = Coordinates(latitude = 50.0619, longitude = 19.9373),
            zoom = 13f
        ),
        markers = listOf(
            Marker(
                coordinates = Coordinates(latitude = 50.0486, longitude = 19.9654),
                title = "Software Mansion",
                androidSnippet = "Software house"
            )
        ),
        circles = listOf(
            Circle(
                center = Coordinates(latitude = 50.0486, longitude = 19.9654),
                radius = 500.0f,
                lineColor = MaterialTheme.colorScheme.primary,
                lineWidth = 1f,
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
            ),
        ),
        onMarkerClick = { marker ->
            println("Marker clicked: ${marker.title}")
        },
        onMapClick = { coordinates ->
            println("Map clicked at: ${coordinates.latitude}, ${coordinates.longitude}")
        },
        onCircleClick = { circle ->
            println("Circle clicked: ${circle.center.latitude}, ${circle.center.longitude}")
        }
    )
}