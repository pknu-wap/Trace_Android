package com.example.home.graph.post.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.theme.CommentField
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.PrimaryDefault
import com.example.designsystem.theme.TraceTheme


private val customTextSelectionColors = TextSelectionColors(
    handleColor = PrimaryDefault,
    backgroundColor = PrimaryDefault.copy(
        alpha = 0.75f
    )
)

@Composable
internal fun TraceCommentField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onAddComment: () -> Unit,
    hint: String = "댓글을 입력하세요",
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        BasicTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Default
            ),
            textStyle = TraceTheme.typography.bodySM.copy(fontSize = 15.sp),
            cursorBrush = SolidColor(PrimaryDefault),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .padding(vertical = 13.dp)
                        .fillMaxWidth()
                       ,
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            style = TraceTheme.typography.bodySM,
                            color = Gray,
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        innerTextField()

                        Spacer(Modifier.weight(1f))

                        Image(
                            painter = painterResource(R.drawable.send_ic),
                            contentDescription = "댓글 작성",
                            modifier = Modifier.clickable {
                                onAddComment()
                            }
                        )
                    }
                }
            },
            modifier = modifier
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .background(CommentField)
                .padding(start = 14.dp, end = 10.dp)
        )
    }
}