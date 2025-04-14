package com.example.home.graph.writepost.component


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.PrimaryDefault
import com.example.designsystem.theme.TraceTheme
import kotlinx.coroutines.launch

private val customTextSelectionColors = TextSelectionColors(
    handleColor = PrimaryDefault,
    backgroundColor = PrimaryDefault.copy(
        alpha = 0.75f
    )
)

@Composable
internal fun TraceTitleField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onNext: () -> Unit,
    hint: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var isFocused by remember { mutableStateOf(false) }


    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        BasicTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    keyboardController?.hide()
                    onNext()
                }
            ),
            textStyle = TraceTheme.typography.bodyMB,
            cursorBrush = SolidColor(PrimaryDefault),
            decorationBox = { innerTextField ->
                if (value.isEmpty() && !isFocused) {
                    Text(
                        text = hint,
                        style = TraceTheme.typography.bodyMB,
                        color = Gray,
                    )
                }
                innerTextField()
            },
            modifier = modifier.onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        )
    }

}

@Composable
internal fun TraceContentField(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
) {
    var isFocused by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var prevHeight by remember { mutableStateOf(0) }


    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        BasicTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Default
            ),
            textStyle = TraceTheme.typography.bodyMM,
            cursorBrush = SolidColor(PrimaryDefault),
            decorationBox = { innerTextField ->
                if (value.isEmpty() && !isFocused) {
                    Text(
                        text = hint,
                        style = TraceTheme.typography.bodyMM,
                        color = Gray,
                    )

                }

                innerTextField()


            },
            modifier = modifier
                .fillMaxWidth()
                .onSizeChanged {
                    val diff = it.height - prevHeight
                    prevHeight = it.height
                    if (prevHeight == 0 || diff == 0) {
                        return@onSizeChanged
                    }

                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(
                            lazyListState.firstVisibleItemIndex,
                            lazyListState.firstVisibleItemScrollOffset + diff
                        )

                    }
                }
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },

            )


    }


}
