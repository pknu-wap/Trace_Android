package com.example.common.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun TraceBottomBarAnimation(
    visible: Boolean,
    modifier: Modifier = Modifier,
    contents: @Composable AnimatedVisibilityScope.() -> Unit,
) = AnimatedVisibility(
    visible = visible,
    enter = fadeIn() + expandVertically(),
    exit = fadeOut() + shrinkVertically(),
    content = contents,
    modifier = modifier,
)
