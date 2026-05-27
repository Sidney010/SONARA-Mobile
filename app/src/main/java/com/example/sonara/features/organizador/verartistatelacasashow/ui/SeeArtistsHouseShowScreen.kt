
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.theme.DarkGradients

@Composable
fun   SeeArtistsHouseShowScreen(modifier: Modifier = Modifier) {

    val gradients = DarkGradients

    ScreenContainer(
        modifier = Modifier.verticalScroll(rememberScrollState()).wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        verticalSpacing = 6.dp,
        padding = PaddingValues(12.dp, 40.dp)
    ) {
        HomeHeader(
            state = HeaderUiState(userName = "Anônimo", userRole = "Usuário"),
            onLogoClick = {},
            onAvatarClick = {},
            onNotificationClick = {}
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(brush = gradients.secondaryCard, shape = RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Artistas Próximos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                val totalArtists = 9
                val rows = (totalArtists + 2) / 3

                repeat(rows) { rowIndex ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val startIndex = rowIndex * 3
                        val endIndex = minOf(startIndex + 3, totalArtists)

                        repeat(endIndex - startIndex) {
                            ArtistCard(
                                modifier = Modifier.weight(1f),
                                filledStars = when (it % 3) {
                                    0 -> 4
                                    1 -> 4
                                    else -> 3
                                }
                            )
                        }


                        repeat(3 - (endIndex - startIndex)) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
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
    filledStars: Int = 4
) {
    Card(
        modifier = modifier.wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5C5B0))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color(0xFFCC2200)),
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
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Column(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(text = "Pedro", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                Text(text = "Artista Musical", fontSize = 11.sp, color = Color.Black.copy(alpha = 0.7f))


                Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {
                    repeat(filledStars) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFCC00),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    repeat(5 - filledStars) {
                        Icon(
                            imageVector = Icons.Default.StarOutline,
                            contentDescription = null,
                            tint = Color(0xFFFFCC00),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                Text(text = "Jandira", fontSize = 11.sp, color = Color.Black.copy(alpha = 0.8f))
                Text(text = "Eletronica & Clássica", fontSize = 10.sp, color = Color.Black.copy(alpha = 0.6f))

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.RemoveRedEye,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(text = "Ver mais", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}
