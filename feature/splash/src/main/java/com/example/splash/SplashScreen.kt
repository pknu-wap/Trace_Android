package com.example.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.R

@Composable
internal fun SplashRoute() {
    SplashScreen()
}

@Composable
private fun SplashScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(0.75f))

        Image(painter = painterResource(R.drawable.app_icon_pencil), contentDescription = "앱 아이콘", modifier = Modifier.weight(0.5f))

        Spacer(Modifier.weight(1f))
    }
}


@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}