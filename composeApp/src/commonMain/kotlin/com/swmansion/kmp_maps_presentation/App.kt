package com.swmansion.kmp_maps_presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swmansion.kmpmaps.CameraPosition
import com.swmansion.kmpmaps.Circle
import com.swmansion.kmpmaps.Coordinates
import com.swmansion.kmpmaps.Map
import com.swmansion.kmpmaps.Marker
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    var lastAction by remember { mutableStateOf<String?>(null) }
    var actionDetails by remember { mutableStateOf<String?>(null) }
    var actionIcon by remember { mutableStateOf("📍") }
    var showNotification by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    
    val scale by animateFloatAsState(
        targetValue = if (showNotification) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (showNotification) 1f else 0f,
        animationSpec = tween(300),
        label = "alpha"
    )

    fun showActionNotification(
        action: String,
        details: String? = null,
        icon: String
    ) {
        lastAction = action
        actionDetails = details
        actionIcon = icon
        showNotification = true

        coroutineScope.launch {
            delay(3000)
            showNotification = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Map(
            modifier = Modifier.fillMaxSize(),
            cameraPosition = CameraPosition(
                coordinates = Coordinates(latitude = 50.0689, longitude = 19.9573),
                zoom = 14f
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
                    center = Coordinates(latitude = 50.06839615782847, longitude = 19.947491884231567),
                    radius = 500.0f,
                    lineColor = MaterialTheme.colorScheme.primary,
                    lineWidth = 1f,
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                ),
            ),
            onMarkerClick = { marker ->
                showActionNotification(
                    action = "Marker Clicked!",
                    details = "${marker.title}\n📍 ${marker.coordinates.latitude}, ${marker.coordinates.longitude}",
                    icon = "📍"
                )
            },
            onMapLongClick = { coordinates ->
                showActionNotification(
                    action = "Map Long Clicked!",
                    details = "🗺️ ${coordinates.latitude}, ${coordinates.longitude}",
                    icon = "🗺️"
                )
            },
            onCircleClick = { circle ->
                showActionNotification(
                    action = "Circle Clicked!",
                    details = "🎯 ${circle.center.latitude}, ${circle.center.longitude}",
                    icon = "🎯"
                )
            }
        )
        
        AnimatedVisibility(
            visible = showNotification,
            enter = slideInVertically(
                initialOffsetY = { -it },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(300)
            ) + fadeOut(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 55.dp, start = 16.dp, end = 16.dp)
        ) {
            Card(
                modifier = Modifier
                    .scale(scale)
                    .alpha(alpha),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.1f),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = actionIcon,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 24.sp
                        ),
                        color = Color.Black
                    )
                    
                    Column {
                        Text(
                            text = lastAction ?: "",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            color = Color.Black
                        )
                        Text(
                            text = actionDetails ?: "",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 12.sp
                            ),
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}