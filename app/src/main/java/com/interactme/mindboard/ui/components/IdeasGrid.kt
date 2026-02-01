package com.interactme.mindboard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.interactme.mindboard.domain.model.Idea
import com.interactme.mindboard.viewmodel.IdeaViewModel

@Composable
fun IdeasGrid(
    items: List<Idea>,
    modifier: Modifier = Modifier,
    viewModel: IdeaViewModel,
    columns: Int = 2,
    topPadding: Dp = 220.dp,
    bottomPadding: Dp = 60.dp
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(columns),
        modifier = modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp)
            .background(Color.Transparent)
            .graphicsLayer(clip = false),
        contentPadding = PaddingValues(top = topPadding, bottom = bottomPadding),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 12.dp,
    ) {
        items(
            items = items,
            key = { idea -> idea.id }
        ) { idea ->
            IdeaCard(
                item = idea,
                modifier = Modifier.fillMaxWidth(),
                onMenuClick = viewModel::onIdeaMenuClick,
                viewModel = viewModel,
            )
        }
    }
}