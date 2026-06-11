package com.example.sonara.features.organizador.pesquisarartistacasadeshow.ui

import com.example.sonara.features.organizador.verartistatelacasashow.ui.ArtistCard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.theme.AppColors

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sonara.features.organizador.pesquisarartistacasadeshow.viewmodel.SearchArtistHouseShowViewModel

@Composable
fun SearchArtistHouseShowScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchArtistHouseShowViewModel = hiltViewModel(),
    onNavigateToProfile: () -> Unit = {},
    onNavigateToArtistDetails: (Int) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    ScreenContainer(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        verticalSpacing = 6.dp,
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            TextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .clip(RoundedCornerShape(16.dp)),
                placeholder = {
                    Text(text = "Pesquisar", color = Color.White, fontSize = 16.sp)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = AppColors.PrimaryColor,
                    unfocusedContainerColor = AppColors.PrimaryColor,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                shape = CircleShape
            )
        }

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = AppColors.PrimaryColor)
            }
        } else if (uiState.filteredArtists.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhum Artista encontrado",
                    color = AppColors.colorFontLogin,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Artistas Encontrados",
                    fontSize = 20.sp,
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
    }
}

// Removido classes mock não utilizadas