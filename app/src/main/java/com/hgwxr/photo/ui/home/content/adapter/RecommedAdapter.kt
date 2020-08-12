package com.hgwxr.photo.ui.home.content.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hgwxr.photo.R
import com.hgwxr.photo.data.model.ContentModel
import com.hgwxr.photo.ui.home.preview.ImagePreviewFragment
import com.hgwxr.photo.utils.loadCircleImage
import com.hgwxr.photo.utils.loadImage

class RecommedAdapter(val fragment: Fragment) :
    ListAdapter<ContentModel, RecommedViewHolder>(DIFF_CALLBACK) {
    lateinit var inflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommedViewHolder {
        inflater = LayoutInflater.from(parent.context)
        val inflate = inflater.inflate(viewType, parent, false)
        return RecommedViewHolder(inflate)
    }

    override fun submitList(list: MutableList<ContentModel>?) {
        super.submitList(list)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        var layoutType = R.layout.item_image_more
        when (item.d_type) {
            "3" -> {
                layoutType = R.layout.item_video
            }
            "1" -> {
                layoutType = when (item.picArr.size) {
                    0 -> {
                        R.layout.item_image
                    }
                    1 -> {
                        R.layout.item_image
                    }
                    2 -> {
                        R.layout.item_image_two
                    }
                    else -> {
                        R.layout.item_image_more
                    }
                }
            }
            else -> {
            }
        }
        return layoutType
    }

    override fun onBindViewHolder(holder: RecommedViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindView(item, position, fragment, inflater)
    }

}

class RecommedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(
        item: ContentModel,
        position: Int,
        fragment: Fragment,
        inflater: LayoutInflater
    ) {
        val tvUserName = itemView.findViewById<TextView>(R.id.userNameTv)
        val ivUser = itemView.findViewById<ImageView>(R.id.headerIv)
        item.userInfo?.let {
            it.baseInfo?.let { baseInfo ->
                tvUserName.text = baseInfo.username
                fragment.loadCircleImage(baseInfo.getFormatAvatar(), ivUser,R.drawable.ic_default_head)
            }
        }
        when (item.d_type) {
            "3" -> {
                val tvVideo = itemView.findViewById<TextView>(R.id.videoText)
                if (!TextUtils.isEmpty(item.text_info)) {
                    tvVideo.isVisible = true
                    tvVideo.text = item.text_info
                } else {
                    tvVideo.isVisible = false
                }
            }
            "1" -> {
                when (item.picArr.size) {
                    0 -> {
                        val tvVideo = itemView.findViewById<TextView>(R.id.videoText)
                        if (!TextUtils.isEmpty(item.text_info)) {
                            tvVideo.isVisible = true
                            tvVideo.text = item.text_info
                        } else {
                            tvVideo.isVisible = false
                        }
                    }
                    1 -> {
                        val tvVideo = itemView.findViewById<TextView>(R.id.videoText)
                        if (!TextUtils.isEmpty(item.text_info)) {
                            tvVideo.isVisible = true
                            tvVideo.text = item.text_info
                        } else {
                            tvVideo.isVisible = false
                        }
                        val image = itemView.findViewById<ImageView>(R.id.mediaContent)
                        val formatPic = item.getFormatPic()
                        if (formatPic.isNotEmpty()) {
                            fragment.loadImage(formatPic[0], image)
                        }
                    }
                    2 -> {
                        val tvVideo = itemView.findViewById<TextView>(R.id.videoText)
                        if (!TextUtils.isEmpty(item.text_info)) {
                            tvVideo.isVisible = true
                            tvVideo.text = item.text_info
                        } else {
                            tvVideo.isVisible = false
                        }
                        val image = itemView.findViewById<ImageView>(R.id.mediaContent)
                        val imageNext = itemView.findViewById<ImageView>(R.id.mediaContentNext)
                        val formatPic = item.getFormatPic()
                        if (formatPic.isNotEmpty()) {
                            fragment.loadImage(formatPic[0], image)
                            fragment.loadImage(formatPic[1], imageNext)
                        }
                    }
                    else -> {
                        val tvVideo = itemView.findViewById<TextView>(R.id.videoText)
                        if (!TextUtils.isEmpty(item.text_info)) {
                            tvVideo.isVisible = true
                            tvVideo.text = item.text_info
                        } else {
                            tvVideo.isVisible = false
                        }
                        val mediaContents = itemView.findViewById<GridLayout>(R.id.mediaContents)
                        mediaContents.isVisible = true
                        mediaContents.removeAllViews()
                        val formatPic = item.getFormatPic()
                        if (formatPic.isNotEmpty()) {
                            formatPic.forEachIndexed { index, str ->
                                if (index < 9) {
                                    val inflate =
                                        inflater.inflate(R.layout.image_unit, mediaContents, false)
                                    inflate?.let {
                                        val imageView = it as ImageView
                                        mediaContents.addView(imageView)
                                        imageView.setOnClickListener {
                                            ImagePreviewFragment.start(fragment, arrayListOf(str))
                                        }
                                        fragment.loadImage(str, imageView)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else -> {
                val tvVideo = itemView.findViewById<TextView>(R.id.videoText)
                if (!TextUtils.isEmpty(item.text_info)) {
                    tvVideo.isVisible = true
                    tvVideo.text = item.text_info
                } else {
                    tvVideo.isVisible = false
                }
                if (item.getFormatPic().isEmpty()) {
                    val mediaContents = itemView.findViewById<GridLayout>(R.id.mediaContents)
                    mediaContents.isVisible = false
                }
            }
        }
    }


}