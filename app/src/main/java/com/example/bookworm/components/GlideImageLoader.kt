package com.example.bookworm.components


import android.widget.ImageView
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@Composable
fun GlideImageLoader(
    imageScale: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP,
    url: String,
    applyCircleCrop: Boolean = false, // Parameter to toggle circle crop
    modifier: Modifier = Modifier
)
{
    AndroidView(
        modifier = modifier,
        factory = { context ->
            ImageView(context).apply {
                scaleType = imageScale
            }
        },
        update = { imageView ->
            Glide.with(imageView.context)
                .load(url)// Image URL
                .apply{if(applyCircleCrop) {
                    circleCrop()
                } }            // Circle crop transformation
                .transition(DrawableTransitionOptions.withCrossFade())// Crossfade
                .into(imageView)

        }
    )
}