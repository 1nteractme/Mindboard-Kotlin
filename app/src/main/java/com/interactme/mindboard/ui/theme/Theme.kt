package com.interactme.mindboard.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.interactme.mindboard.R

private val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        white = Color.Unspecified,
        black = Color.Unspecified,
        appBg = Color.Unspecified,
        appSurface = Color.Unspecified,
        appDivider = Color.Unspecified,
        textPrimary = Color.Unspecified,
        textSecondary = Color.Unspecified,
        textMuted = Color.Unspecified,
        textLight = Color.Unspecified,
        groupSystemAll = Color.Unspecified,
        groupSystemFavorites = Color.Unspecified,
        groupDefault = Color.Unspecified,
        ideaPalette = emptyList()
    )
}

object MindBoardTheme {
    val colors: AppColors
        @Composable get() = LocalAppColors.current
}

@Composable
fun MindBoardTheme(
    content: @Composable () -> Unit
) {
    val colors = AppColors(
        white = colorResource(R.color.white),
        black = colorResource(R.color.black),
        appBg = colorResource(R.color.app_bg),
        appSurface = colorResource(R.color.app_surface),
        appDivider = colorResource(R.color.app_divider),
        textPrimary = colorResource(R.color.text_primary),
        textSecondary = colorResource(R.color.text_secondary),
        textMuted = colorResource(R.color.text_muted),
        textLight = colorResource(R.color.text_light),
        groupSystemAll = colorResource(R.color.group_system_all),
        groupSystemFavorites = colorResource(R.color.group_system_favorites),
        groupDefault = colorResource(R.color.group_default),
        ideaPalette = listOf(
            colorResource(R.color.idea_color_1),
            colorResource(R.color.idea_color_2),
            colorResource(R.color.idea_color_3),
            colorResource(R.color.idea_color_4),
            colorResource(R.color.idea_color_5),
            colorResource(R.color.idea_color_6),
            colorResource(R.color.idea_color_7),
        )
    )

    val colorScheme = darkColorScheme(
        primary = colors.groupSystemAll,
        onPrimary = colors.white,
        secondary = colors.groupSystemFavorites,
        onSecondary = colors.textPrimary,
        tertiary = colors.groupDefault,
        onTertiary = colors.textPrimary,
        background = colors.appBg,
        onBackground = colors.textLight,
        surface = colors.appSurface,
        onSurface = colors.textLight,
        surfaceVariant = colors.appDivider,
        onSurfaceVariant = colors.textSecondary,
        outline = colors.appDivider,
    )

    CompositionLocalProvider(LocalAppColors provides colors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography(),
            content = content
        )
    }
}
