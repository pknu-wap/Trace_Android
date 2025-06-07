package com.example.common.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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

fun defaultSlideFadeIn() = slideInHorizontally(
    animationSpec =
        tween(700)
) { it } + fadeIn(animationSpec = tween(500))

fun defaultSlideFadeOut() = slideOutHorizontally(
    animationSpec = tween(700)
) { -it } + fadeOut(animationSpec = tween(500))

fun etaSlideIn(isBack: Boolean = false) = slideInHorizontally(
    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
) { if (isBack) -it else it }

fun etaSlideOut(isBack: Boolean = false) = slideOutHorizontally(
    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
) { if (isBack) it else -it }