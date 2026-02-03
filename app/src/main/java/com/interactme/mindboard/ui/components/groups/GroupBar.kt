package com.interactme.mindboard.ui.components.groups

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.interactme.mindboard.ui.components.CustomButton
import com.interactme.mindboard.ui.components.CustomText
import com.interactme.mindboard.ui.components.glass
import com.interactme.mindboard.domain.model.Group
import com.interactme.mindboard.ui.theme.MindBoardTheme
import io.github.fletchmckee.liquid.LiquidState

@Composable
fun GroupBar(
    liquidState: LiquidState,
    isGlassEnabled: Boolean,
    groups: List<Group>,
    selectedGroupId: String?,
    onGroupSelected: (String) -> Unit = {},
    onAddGroupClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onEditGroupClick: (String) -> Unit = {},
    radius: Dp = 25.dp
) {
    val selectedGroup = groups.find { it.id == selectedGroupId }
    val canEditSelected = selectedGroup?.isSystem == false
    val isFocused = selectedGroupId != null && selectedGroupId != Group.ALL_ID
    val visibleGroups = if (isFocused) {
        groups.filter { it.id == selectedGroupId }
    } else {
        groups
    }

    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(60.dp)
            .clip(RoundedCornerShape(radius))
            .glass(liquidState, RoundedCornerShape(radius), isGlassEnabled)
            .background(Color.Transparent)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(10.dp, 10.dp, 0.dp, 10.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isFocused) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = MindBoardTheme.colors.white
                    )
                }
            } else {
                CustomButton(onAddGroupClick, Icons.Rounded.Add)
            }

            if (canEditSelected) {
                IconButton(onClick = { onEditGroupClick(selectedGroup!!.id) }) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Edit Group",
                        tint = MindBoardTheme.colors.white
                    )
                }
            }

            Box(
                Modifier
                    .width(2.dp)
                    .fillMaxHeight()
                    .background(MindBoardTheme.colors.appDivider)
            )

            LazyRow(
                Modifier.graphicsLayer(clip = false),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(end = 12.dp)
            ) {
                items(visibleGroups.size) { index ->
                    val group = visibleGroups[index]
                    val isSelected = group.id == selectedGroupId
                    val backgroundColor = if (isSelected) {
                        Color(group.color.toInt())
                    } else {
                        Color(group.color.toInt()).copy(alpha = 0.7f)
                    }

                    key(group.id) {
                        Box(
                            Modifier
                                .fillMaxHeight()
                                .wrapContentWidth()
                                .background(backgroundColor, RoundedCornerShape(radius))
                                .clickable { onGroupSelected(group.id) }
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            Alignment.Center,
                        ) {
                            CustomText(
                                title = group.name,
                                titleColor = MindBoardTheme.colors.textSecondary,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}