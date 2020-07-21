package com.hgwxr.photo.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.hgwxr.photo.R
import com.hgwxr.photo.utils.snackBar
import kotlinx.android.synthetic.main.layout_follow_user.view.*


class FollowUserView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    init {
      LayoutInflater.from(context).inflate(R.layout.layout_follow_user,this,true)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val shareClick: (v: View) -> Unit = {
            it.snackBar("分享").show()
        }
        shareView.setOnClickListener(shareClick)
        shareTextView.setOnClickListener(shareClick)

        val commentClick: (v: View) -> Unit = {
            it.snackBar("评论").show()
        }
        commentView.setOnClickListener(commentClick)
        commentTextView.setOnClickListener(commentClick)

        val likeClick: (v: View) -> Unit = {
            it.snackBar("喜欢").show()
        }
        likeView.setOnClickListener(likeClick)
        likeTextView.setOnClickListener(likeClick)
    }

}