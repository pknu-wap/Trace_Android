package com.example.home.writepost.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.example.common.util.clickable
import com.example.designsystem.theme.Black
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.White


@Composable
internal fun ImageContent(
    images: List<Uri>,
    removeImage: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(0, ) { images.size }

    if (images.isNotEmpty()) {
        Spacer(Modifier.height(10.dp))

        HorizontalPager(
            state = pagerState,
            modifier = modifier.fillMaxWidth()
        ) { pageIndex ->
            val imagePainter = rememberAsyncImagePainter(images[pageIndex])

            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(horizontal = 3.dp)
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Image(
                        painter = imagePainter,
                        contentDescription = "Image $pageIndex",
                        contentScale = ContentScale.Crop
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .size(35.dp)
                        .background(Black.copy(alpha = 0.7f), shape = RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${pageIndex + 1} / ${images.size}",
                        color = White,
                        style = TraceTheme.typography.bodySM.copy(fontSize = 12.sp)
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(30.dp)
                        .background(Black.copy(0.8f), shape = CircleShape)
                        .clickable {
                            removeImage(images[pageIndex])
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "이미지 삭제",
                        tint = White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }


        Spacer(Modifier.height(15.dp))
    }
}