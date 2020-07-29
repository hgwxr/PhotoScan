package com.hgwxr.photo.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hgwxr.photo.R
import com.hgwxr.photo.utils.GlideApp

class PicViews @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {



    private fun createImageView(): ImageView {
        val imageView = ImageView(context)
        val layoutParams = LayoutParams(
            0,
            LayoutParams.MATCH_PARENT
        )
        layoutParams.weight = 1.0f
        addView(imageView, layoutParams)
        return imageView
    }

    fun setImages(urls: List<String>) {
        if (childCount > 0) {
            return
        }
        when (urls.size) {
            1 -> {
                val imageView = createImageView()
                loadImage(urls[0], imageView)
            }
            2 -> {
                val imageViewOne = createImageView()
                loadImage(urls[0], imageViewOne)
                val imageViewTwo = createImageView()
                loadImage(urls[1], imageViewTwo)
            }
            else -> {
                urls.forEachIndexed { index, url ->
                    run {
                        if (index < 3) {
                            val imageView = createImageView()
                            loadImage(url, imageView)
                        }
                    }
                }
            }
        }
    }

    private fun loadImage(url: String, target: ImageView) {
        GlideApp.with(context).load(url)
            .apply(
                RequestOptions
                    .bitmapTransform(MultiTransformation(CenterCrop(),RoundedCorners(10)))
                    .placeholder(R.drawable.ic_placeholder_image)
                    .error(R.drawable.ic_placeholder_image)
            )
            .into(target)

    }
}