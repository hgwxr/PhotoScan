package com.hgwxr.photo.utils

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hgwxr.photo.R

fun Fragment.loadImage(url:String,imageView: ImageView){
    GlideApp.with(this).load(url)
        .apply(
            RequestOptions
                .bitmapTransform(MultiTransformation(CenterCrop(), RoundedCorners(10)))
                .placeholder(R.drawable.ic_placeholder_image)
                .error(R.drawable.ic_placeholder_image)
        )
        .into(imageView)
}