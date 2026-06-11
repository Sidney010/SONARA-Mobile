package com.example.sonara.features.organizador.verartistatelacasashow.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.theme.AppColors
import com.example.sonara.data.remote.dto.response.usuario.ArtistaDto
import com.example.sonara.features.organizador.verartistatelacasashow.viewmodel.SeeArtistsHouseShowViewModel
import com.google.android.gms.location.LocationServices

@Composable
fun SeeArtistsHouseShowScreen(
    modifier: Modifier = Modifier,
    viewModel: SeeArtistsHouseShowViewModel = hiltViewModel(),
    onNavigateToProfile: () -> Unit = {},
    onNavigateToArtistDetails: (Int) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let { viewModel.onLocationUpdate(it) }
                }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let { viewModel.onLocationUpdate(it) }
            }
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    ScreenContainer(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        verticalSpacing = 16.dp,
        padding = PaddingValues(12.dp, 40.dp)
    ) {
        HomeHeader(
            state = HeaderUiState(
                userName = uiState.userName,
                userRole = uiState.userRole,
                avatarUrl = uiState.avatarUrl,
                isLoggedIn = uiState.isLoggedIn
            ),
            onLogoClick = {},
            onAvatarClick = onNavigateToProfile,
            onNotificationClick = {}
        )

        // Barra de Pesquisa
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Pesquisar por nome, gênero ou cidade", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = AppColors.PrimaryColor) },
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = AppColors.PrimaryColor,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                singleLine = true
            )

            IconButton(
                onClick = { viewModel.toggleLocationFilter(!uiState.isLocationFilterEnabled) },
                modifier = Modifier
                    .background(
                        if (uiState.isLocationFilterEnabled) AppColors.PrimaryColor else Color.White,
                        CircleShape
                    )
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Filtrar por localização",
                    tint = if (uiState.isLocationFilterEnabled) Color.White else AppColors.PrimaryColor
                )
            }
        }

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = AppColors.PrimaryColor)
            }
        } else if (uiState.errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = uiState.errorMessage!!, color = Color.Red)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = AppColors.PrimaryColor, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = if (uiState.isLocationFilterEnabled) "Artistas Próximos" else "Todos os Artistas",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.colorFontLogin
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.filteredArtists) { artista ->
                        ArtistCard(
                            artista = artista,
                            onClick = { onNavigateToArtistDetails(artista.idUsuario) }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun ArtistCard(
    modifier: Modifier = Modifier,
    artista: ArtistaDto? = null,
    onClick: () -> Unit = {},
    filledStars: Int = 4
) {
    val displayNome = artista?.nomeArtistico ?: artista?.nome ?: "Nome do Artista"
    val displayCidade = artista?.cidade ?: "Localização n/a"
    val displayGenero = artista?.genero ?: "Gênero n/a"

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(AppColors.colorFontLogin),
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .offset(y = 24.dp)
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    if (artista?.foto != null) {
                        AsyncImage(
                            model = artista.foto,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Column(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = displayNome,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.colorFontLogin,
                    maxLines = 1
                )
                Text(
                    text = "Artista Musical",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )

                Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {
                    repeat(5) {
                        Icon(
                            imageVector = if (it < filledStars) Icons.Default.Star else Icons.Default.StarOutline,
                            contentDescription = null,
                            tint = Color(0xFFFFCC00),
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }

                Text(
                    text = displayCidade,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Text(
                    text = displayGenero,
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp,
                    color = Color.DarkGray,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.RemoveRedEye,
                        contentDescription = null,
                        tint = AppColors.PrimaryColor,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "Ver mais",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = AppColors.PrimaryColor
                    )
                }
            }
        }
    }
}
