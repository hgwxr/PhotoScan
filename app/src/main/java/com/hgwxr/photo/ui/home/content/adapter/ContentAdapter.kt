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
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_pics, parent, false)
        return ContentViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bindView(getItem(position), fg)
    }


}


class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(contentModel: ContentModel, fg: Fragment) {
        val picArr = contentModel.picArr
        if (picArr.isNotEmpty()) {
            val imageViewN = itemView.findViewById<ImageView>(R.id.contextImage)
            val localConfigModel = LocalRepository.getLocalConfigModel()
            val pic = picArr[0]
            localConfigModel?.let {
                val imgHost = it.getImgHost()
                GlideApp.with(fg).load(imgHost + pic).into(imageViewN)
            }
        }
        val tvInfo = itemView.findViewById<TextView>(R.id.textInfo)
        contentModel.text_info.let {
            tvInfo.text = it
        }
    }
}