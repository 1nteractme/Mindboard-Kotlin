package com.interactme.mindboard.ui.components.addIdea

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.interactme.mindboard.ui.components.CustomText
import com.interactme.mindboard.ui.theme.MindBoardTheme

@Composable
fun ImagePicker(
    imageUri: String?,
    onPickImage: () -> Unit,
    onRemoveImage: () -> Unit
) {
    Column {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            TextButton(onClick = onRemoveImage) {
                Text("Remove image")
            }
        } else {
            Button(
                onClick = onPickImage,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MindBoardTheme.colors.appBg,
                    contentColor = MindBoardTheme.colors.white
                ),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                CustomText("Add Image", MindBoardTheme.colors.textLight, 16.sp)
            }
        }
    }
}
