package com.interactme.mindboard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.interactme.mindboard.domain.model.Idea
import com.interactme.mindboard.ui.theme.MindBoardTheme
import com.interactme.mindboard.ui.viewmodel.IdeaViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@Composable
fun IdeaCard(
    item: Idea,
    viewModel: IdeaViewModel,
    modifier: Modifier = Modifier,
    onMenuClick: (Idea) -> Unit
) {
    val selectedIdea by viewModel.selectedIdea.collectAsState()
    val isDialogVisible = selectedIdea?.id == item.id

    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(25.dp))
                .background(Color(item.color.toInt()))
        ) {
            Box(modifier = Modifier.matchParentSize())

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                AsyncImage(
                    model = item.imageUri,
                    contentDescription = "Idea Preview",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillHeight,
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 12.dp, start = 6.dp, end = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CustomText(
                        title = item.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W900,
                        titleColor = MindBoardTheme.colors.black.copy(alpha = 0.8f)
                    )

                    CustomText(
                        title = item.description,
                        fontSize = 16.sp,
                        titleColor = MindBoardTheme.colors.black.copy(alpha = 0.8f)
                    )

                    CustomText(
                        title = formatDate(item.createdAt),
                        fontSize = 12.sp,
                        titleColor = MindBoardTheme.colors.black.copy(alpha = 0.6f)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MindBoardTheme.colors.white.copy(alpha = 0.7f))
                    .clickable { viewModel.toggleFavorite(item.id) }
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (item.isFavorite) Icons.Rounded.Star else Icons.Rounded.StarBorder,
                    contentDescription = "Favorite",
                    tint = MindBoardTheme.colors.textPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.End)
                .clickable { if (!isDialogVisible) onMenuClick(item) else viewModel.dismissDialog() }
                .clip(RoundedCornerShape(25.dp))
                .background(Color(item.color.toInt()))
        ) {
            Icon(
                imageVector = if (!isDialogVisible) Icons.Rounded.MoreHoriz else Icons.Rounded.Close,
                contentDescription = "Edit",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
                tint = MindBoardTheme.colors.black.copy(alpha = 0.8f)
            )
        }
    }
}
