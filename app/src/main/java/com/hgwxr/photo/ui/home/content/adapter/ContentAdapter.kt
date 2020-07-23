package com.hgwxr.photo.ui.home.content.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hgwxr.photo.R
import com.hgwxr.photo.data.LocalRepository
import com.hgwxr.photo.data.model.ContentModel
import com.hgwxr.photo.utils.GlideApp
import com.hgwxr.photo.widgets.PicViews

val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ContentModel>() {
    override fun areItemsTheSame(oldItem: ContentModel, newItem: ContentModel): Boolean {
        return TextUtils.equals(oldItem.id, newItem.id)
    }

    override fun areContentsTheSame(oldItem: ContentModel, newItem: ContentModel): Boolean {
        return oldItem.equals(newItem)
    }
}

class ContentAdapter(private val fg: Fragment) :
    ListAdapter<ContentModel, ContentViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ContentViewHolder(inflate)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        when (item.level) {
            "0" -> {
                return R.layout.item_three_pic
            }
            "1" -> {
                return R.layout.item_pics
            }
            "2" -> {
                return R.layout.item_pics
            }
            "3" -> {
                return R.layout.item_pics
            }
            "4" -> {
                return R.layout.item_pics
            }
            else -> {
                return R.layout.item_pics
            }
        }
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bindView(getItem(position), fg)
    }
}


class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindTypeThreePics(contentModel: ContentModel, fg: Fragment) {
        val threeImgViews = itemView.findViewById<PicViews>(R.id.ThreeImgViews)
        val threeContentTitleTv = itemView.findViewById<TextView>(R.id.threeContentTitleTv)
        val localConfigModel = LocalRepository.getLocalConfigModel()
        val picArr = contentModel.picArr
        if (picArr.isNotEmpty()) {
            val pic = picArr[0]
            localConfigModel?.let {
                val imgHost = it.getImgHost()
                threeImgViews.setImages(mutableListOf(imgHost + pic))
            }
        }
        contentModel.text_info.let {
            threeContentTitleTv.text = it
        }
    }

    fun bindView(contentModel: ContentModel, fg: Fragment) {
        when (contentModel.level) {
            "0" -> {
                bindTypeThreePics(contentModel, fg)
            }
            else -> {
                val picArr = contentModel.picArr
                if (picArr.isNotEmpty()) {
                    val imageViewN = itemView.findViewById<ImageView>(R.id.contextImage)
                    val contentNumbersTv = itemView.findViewById<TextView>(R.id.contentNumbers)
                    val localConfigModel = LocalRepository.getLocalConfigModel()
                    val pic = picArr[0]
                    localConfigModel?.let {
                        val imgHost = it.getImgHost()
                        GlideApp.with(fg).load(imgHost + pic).into(imageViewN)
                    }
                    contentNumbersTv.text = "1/${picArr.size}"
                }
                val tvInfo = itemView.findViewById<TextView>(R.id.textInfo)
                contentModel.text_info.let {
                    tvInfo.text = it
                }
            }
        }

    }
}