package com.example.home.graph.writepost.component


import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
                Row {
                    if (value.isEmpty() && !isFocused) {
                        Text(
                            text = hint,
                            style = TraceTheme.typography.bodyMB,
                            color = Gray,
                        )
                    }
                    innerTextField()

                }
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
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
) {
    var isFocused by remember { mutableStateOf(false) }

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
                Row {
                    if (value.isEmpty() && !isFocused) {
                        Text(
                            text = hint,
                            style = TraceTheme.typography.bodyMM,
                            color = Gray,
                        )
                    }
                }
                innerTextField()

            },
            modifier = modifier.onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        )
    }

}
