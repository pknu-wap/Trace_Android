package com.example.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.designsystem.R


val BookkBold = FontFamily(
    Font(
        resId = R.font.bookk_myungjo_bold,
        weight = FontWeight.Bold
    )
)

@Immutable
data class TraceTypography(
    val headingLB: TextStyle = TextStyle(
        fontFamily = BookkBold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    )
)