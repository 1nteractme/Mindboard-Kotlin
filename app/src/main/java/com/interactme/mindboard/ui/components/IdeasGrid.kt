package com.interactme.mindboard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.interactme.mindboard.domain.model.Idea
import com.interactme.mindboard.viewmodel.IdeaViewModel

@Composable
fun IdeasGrid(
    items: List<Idea>,
    modifier: Modifier = Modifier,
    columns: Int = 2,
    viewModel: IdeaViewModel,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(columns),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = 140.dp,
            bottom = 20.dp,
            start = 12.dp,
            end = 12.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 12.dp
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