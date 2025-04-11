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

val PretendardSemiBold = FontFamily(
    Font(
        resId = R.font.pretendard_semi_bold,
        weight = FontWeight.SemiBold
    )
)
val PretendardMedium = FontFamily(
    Font(
        resId = R.font.pretendard_semi_bold,
        weight = FontWeight.Medium
    )
)


@Immutable
data class TraceTypography(
    val headingLB: TextStyle = TextStyle(
        fontFamily = BookkBold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    val headingMB: TextStyle = TextStyle(
        fontFamily = BookkBold,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),
    val bodyMM: TextStyle = TextStyle(
        fontFamily = PretendardMedium,
        fontSize = 18.sp,
        lineHeight = 22.sp
    ),
    val bodySM: TextStyle = TextStyle(
        fontFamily = PretendardMedium,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    val bodyMSB: TextStyle = TextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),
    val bodySSB: TextStyle = TextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp
    )


)