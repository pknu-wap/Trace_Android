package com.example.home.graph.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.White

@Composable
internal fun HomeDropDownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onRefresh: () -> Unit,
    onWritePost: () -> Unit,
) {
    if (expanded) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss,
            modifier = Modifier
                .shadow(elevation = 1.dp, RoundedCornerShape(8.dp))
                .background(White),
        ) {
            Row(
                modifier = Modifier
                    .clickable(isRipple = true) {
                        onDismiss()
                        onRefresh()
                    }
                    .padding(top = 15.dp, bottom = 15.dp, start = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = "새로고침",
                    modifier = Modifier.size(30.dp)
                )

                Spacer(Modifier.width(12.dp))

                Text(stringResource(R.string.refresh), style = TraceTheme.typography.bodyMR)

                Spacer(Modifier.width(70.dp))
            }

            Row(
                modifier = Modifier
                    .clickable(isRipple = true) {
                        onDismiss()
                        onWritePost()
                    }
                    .padding(top = 15.dp, bottom = 15.dp, start = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.pencil),
                    contentDescription = "글 쓰기",
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(18.dp))

                Text(stringResource(R.string.write_post), style = TraceTheme.typography.bodyMR)

                Spacer(Modifier.width(70.dp))
            }

        }
    }

}