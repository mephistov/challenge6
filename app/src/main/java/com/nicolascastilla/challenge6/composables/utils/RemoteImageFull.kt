package com.nicolascastilla.challenge6.composables.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter


@Composable
fun RemoteImageFull(url: String, modifier: Modifier) {
    val imagePainter = rememberImagePainter(
        data = url,
        builder = {
            // Configuración de opciones adicionales (opcional)
            // Ejemplo: establecer una estrategia de caché personalizada
            // cache(CachePolicy.DISABLED) // Deshabilitar la caché
        }
    )

    Image(
        painter = imagePainter,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .clip(CircleShape)
    )
}