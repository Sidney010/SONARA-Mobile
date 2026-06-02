package com.example.sonara.features.artista.sobreEvento.ui.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

/**
 * Componente de mapa reutilizável que encapsula o OSMDroid (OpenStreetMap).
 * Usa AndroidView para integrar a View tradicional no Compose.
 *
 * @param latitude  Coordenada vertical do local do evento
 * @param longitude Coordenada horizontal do local do evento
 * @param title     Título exibido no marcador do mapa
 */
@Composable
fun EventMapView(
    latitude: Double,
    longitude: Double,
    title: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Inicializa config do OSMDroid uma única vez
    // O userAgentValue é obrigatório pela política de uso do OpenStreetMap
    Configuration.getInstance().apply {
        userAgentValue = context.packageName
        load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
    }

    // 'remember' garante que o MapView não seja recriado em recomposições
    val mapView = remember {
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            isClickable = true
        }
    }

    // DisposableEffect garante que o mapa seja destruído corretamente
    // quando o Composable sair da tela (evita memory leak)
    DisposableEffect(Unit) {
        mapView.onResume()
        onDispose {
            mapView.onPause()
        }
    }

    AndroidView(
        factory = { mapView },
        modifier = modifier,
        update = { map ->
            val geoPoint = GeoPoint(latitude, longitude)

            // Centraliza e aplica zoom no local do evento
            map.controller.apply {
                setZoom(16.0)
                setCenter(geoPoint)
            }

            // Limpa marcadores anteriores antes de adicionar novo
            // (evita duplicação em recomposições)
            map.overlays.clear()

            val marker = Marker(map).apply {
                position = geoPoint
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                this.title = title
            }

            map.overlays.add(marker)
            map.invalidate()
        }
    )
}