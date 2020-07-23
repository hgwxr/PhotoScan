package com.hgwxr.photo.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.request.RequestOptions
import com.hgwxr.photo.R
import com.hgwxr.photo.utils.GlideApp

class PicViews @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    fun setImages(urls: List<String>) {
        when (urls.size) {
            1 -> {
                val imageView = ImageView(context)
                val layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
                )
//                imageView.adjustViewBounds = true
//                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                addView(imageView, layoutParams)
                loadImage(urls[0], imageView)
            }
            else -> {
            }
        }
    }

    private fun loadImage(url: String, target: ImageView) {
        GlideApp.with(context).load(url)
            .apply(
                RequestOptions().placeholder(R.drawable.ic_placeholder_image)
                    .error(R.drawable.ic_placeholder_image)
            )
            .centerCrop()
            .into(target)
    }
}