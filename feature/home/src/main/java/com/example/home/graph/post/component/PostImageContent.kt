package com.example.home.graph.post.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.example.designsystem.theme.Black
import com.example.designsystem.theme.ImagePlaceHolder
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.White

/**
 * Displays a horizontally scrollable pager of images with a page indicator overlay.
 *
 * Each image is shown inside a card with rounded corners and cropped to fill the available space.
 * A semi-transparent overlay at the bottom-right corner displays the current image index and total count.
 *
 * @param images List of image URLs to display in the pager.
 * @param modifier Modifier for styling and layout customization.
 */
@Composable
internal fun PostImageContent(
    images: List<String>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(0) { images.size }

    if (images.isNotEmpty()) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier.fillMaxWidth()
        ) { pageIndex ->
            val imagePainter = rememberAsyncImagePainter(
                model = images[pageIndex],
                placeholder = ColorPainter(ImagePlaceHolder)
            )

            Box(
                modifier = modifier.fillMaxWidth(),
            ) {
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(horizontal = 3.dp)
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(12.dp)),
                ) {
                    Image(
                        painter = imagePainter,
                        contentDescription = "Image $pageIndex",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Box(
                    modifier = modifier
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
            }
        }
    }
}


