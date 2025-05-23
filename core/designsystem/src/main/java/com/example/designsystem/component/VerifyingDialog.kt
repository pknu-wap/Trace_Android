package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.PrimaryDefault
import com.example.designsystem.theme.TraceTheme

@Composable
fun VerifyingDialog(
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(horizontal = 40.dp, vertical = 100.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = PrimaryDefault,
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text("당신의 따뜻한 흔적을 인증 중이에요.", style = TraceTheme.typography.bodyMM)

                Spacer(Modifier.height(5.dp))

                Text("조금만 기다려주세요!", style = TraceTheme.typography.bodyMM)
            }
        }
    }
}

@Preview
@Composable
fun VerifyingDialogPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        VerifyingDialog()
    }
}
