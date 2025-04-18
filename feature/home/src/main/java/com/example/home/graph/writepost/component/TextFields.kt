package com.example.home.graph.writepost.component


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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

/**
 * Displays a single-line text input field for entering a title with a custom placeholder and keyboard action.
 *
 * Shows a hint when the input is empty and applies custom text selection colors and theme styles.
 * Triggers the provided callback when the "Next" IME action is pressed.
 *
 * @param value The current text value of the input field.
 * @param onValueChange Callback invoked when the text value changes.
 * @param onNext Callback invoked when the "Next" IME action is pressed.
 * @param hint The placeholder text shown when the input is empty.
 * @param keyboardType The type of keyboard to display; defaults to text.
 */
@Composable
internal fun TraceTitleField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onNext: () -> Unit,
    hint: String = "제목",
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

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
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        style = TraceTheme.typography.bodyMB,
                        color = Gray,
                    )
                }
                innerTextField()
            },
            modifier = modifier
        )
    }

}

/**
 * Displays a multi-line text input field with a hint and custom text selection colors, automatically scrolling the associated list when the field's height changes.
 *
 * @param lazyListState The state of the lazy list to scroll when the text field's height changes.
 * @param value The current text input value.
 * @param onValueChange Callback invoked when the text input value changes.
 * @param hint Placeholder text shown when the input is empty.
 */
@Composable
internal fun TraceContentField(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
) {
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
                if (value.isEmpty()) {
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
            )
    }
}
