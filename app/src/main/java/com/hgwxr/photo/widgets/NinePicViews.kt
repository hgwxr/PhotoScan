package com.hgwxr.photo.widgets

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.children
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hgwxr.photo.R
import com.hgwxr.photo.utils.GlideApp


class NinePicViews @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var itemWidth: Float = 0.0f
    var itemHeight: Float = 0.0f

    val columnCount = 3
    var spaceW = 0
    var spaceH = 0

    init {
        spaceW = 0
        spaceH = 0
    }

    fun dp2px(dp: Int): Float {
        val dip = dp
        val r: Resources = resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip.toFloat(),
            r.displayMetrics
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        itemWidth = (w - spaceW * columnCount) / 3.0f
        itemHeight = itemWidth
    }

    private fun createImageView(): ImageView {
        val imageView = justCreateImageView()
        val layoutParams = LayoutParams(itemWidth.toInt(), itemHeight.toInt())
        addView(imageView, layoutParams)
        return imageView
    }

    private fun justCreateImageView(): ImageView {
        return ImageView(context)
    }

    var mUrls: List<String> = listOf()
    fun layoutChild(view: View, left: Int, top: Int) {
        val layoutParams = view.layoutParams
        val w = itemWidth.toInt()
        layoutParams.width = w
        val h = itemHeight.toInt()
        layoutParams.height = h
        view.layoutParams = layoutParams
        view.layout(
            left, top,
            (left + itemWidth + spaceW).toInt(),
            (top + itemHeight + spaceH).toInt()
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val childrenView = children.toList()
        var rowT = 0
        var leftC = 0
        var topC = 0
        var rightC = 0
        var bottomC = 0
        val fl = itemHeight + spaceH
//        if (childrenView.size > 0) {
//            Log.e("onLayout", "childrenView0 :" + childrenView.size)
//            layoutChild(childrenView[0], leftC, topC)
//        }
//        if (childrenView.size > 1) {
//            Log.e("onLayout", "childrenView1 :" + childrenView.size)
//            leftC = (leftC + itemWidth).toInt()
//            layoutChild(childrenView[1], leftC, topC)
//        }
        childrenView.forEachIndexed { index, view ->
            run {
                topC = ((index / columnCount) * itemHeight).toInt()
                if (index % columnCount == 0) {
                    leftC = 0
                    Log.e("forEachIndexed","==1>"+leftC+"  "+topC)

                } else {
                    Log.e("forEachIndexed","==2>"+leftC+"  "+(index / columnCount) * itemWidth)
                    leftC = (leftC + (index / columnCount) * itemWidth).toInt()
                }
                leftC = (leftC + itemWidth).toInt()
                Log.e("forEachIndexed","==>"+leftC+"  "+topC)
                layoutChild(view, leftC, topC)
            }
        }

//        childrenView.forEachIndexed { _index, view ->
//            Log.e("onLayout","childrenView  forEachIndexed:"+_index)
//
//            if (_index % columnCount == 0) {
//                val i = _index / columnCount
//                topC = (topC + fl * i).toInt()
//                leftC = paddingLeft
//                rightC = leftC + itemWidth.toInt()
//                bottomC = topC + fl.toInt()
//            } else {
//                val i = _index % columnCount
//                val i1 = _index / columnCount
//                topC = (topC + i1 * (fl)).toInt()
//                leftC = (leftC + itemWidth * spaceW).toInt()
//                rightC = leftC + itemWidth.toInt()
//                bottomC = topC + fl.toInt()
//            }
//            val layoutParams = view.layoutParams
//            val w = itemWidth.toInt()
//            layoutParams.width = w
//            val h = itemHeight.toInt()
//            layoutParams.height = h
//            view.layoutParams = layoutParams
//
//            view.layout(leftC, topC, rightC, bottomC)
//        }

//        super.onLayout(changed, left, top, right, bottom)
    }

    fun setImages(urls: List<String>) {
        mUrls = urls
        if (mUrls.isNotEmpty()) {
            removeAllViews()
        }
        mUrls.forEach { _url ->
            kotlin.run {
                val justCreateImageView = justCreateImageView()
                loadImage(_url, justCreateImageView)
                addView(justCreateImageView)
            }
        }
//        loadImage(it,justCreateImageView)
//        if (childCount > 0) {
//            return
//        }
//        when (urls.size) {
//            else -> {
//                urls.forEachIndexed { index, url ->
//                    run {
//                        if (index < 9) {
//                            val imageView = createImageView()
//                            loadImage(url, imageView)
//                        }
//                    }
//                }
//            }
//        }
    }

    private fun loadImage(url: String, target: ImageView) {
        GlideApp.with(context).load(url)
            .apply(
                RequestOptions
                    .bitmapTransform(MultiTransformation(CenterCrop(), RoundedCorners(10)))
                    .placeholder(R.drawable.ic_placeholder_image)
                    .error(R.drawable.ic_placeholder_image)
            )
            .into(target)

    }
}