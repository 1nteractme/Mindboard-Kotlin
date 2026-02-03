package com.interactme.mindboard.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.interactme.mindboard.ui.theme.MindBoardTheme

@Composable
fun CustomText(
    title: String,
    titleColor: Color = Color.Unspecified,
    fontSize: TextUnit = 30.sp,
    fontWeight: FontWeight = FontWeight.W600,
    textAlign: TextAlign = TextAlign.Center,
    modifier: Modifier = Modifier.fillMaxWidth(),
) {
    val resolvedColor = if (titleColor == Color.Unspecified) {
        MindBoardTheme.colors.white
    } else {
        titleColor
    }
    Text(
        text = title,
        color = resolvedColor,
        fontSize = fontSize,
        fontFamily = FontFamily.Monospace,
        fontWeight = fontWeight,
        textAlign = textAlign,
        modifier = modifier,
    )
}
