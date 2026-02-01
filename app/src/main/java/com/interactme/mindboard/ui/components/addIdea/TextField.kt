package com.interactme.mindboard.ui.components.addIdea

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.interactme.mindboard.ui.components.CustomText

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        maxLines = maxLines,
        textStyle = LocalTextStyle.current.copy(
            color = Color.White,
            fontSize = 16.sp
        ),
        cursorBrush = SolidColor(Color.White),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color(0xFF1F1F1F))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                if (value.isEmpty()) {
                    CustomText(label, Color(0xFFEEEEEE), 16.sp)
                }
                innerTextField()
            }
        }
    )
}