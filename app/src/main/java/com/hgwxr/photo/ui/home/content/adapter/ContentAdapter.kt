package com.hgwxr.photo.ui.home.content.adapter

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hgwxr.photo.R
import com.hgwxr.photo.data.LocalRepository
import com.hgwxr.photo.data.model.ContentModel
import com.hgwxr.photo.permission.PermissionHelper
import com.hgwxr.photo.ui.home.preview.ImagePreviewFragment
import com.hgwxr.photo.utils.GlideApp
import com.hgwxr.photo.widgets.PicViews

val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ContentModel>() {
    override fun areItemsTheSame(oldItem: ContentModel, newItem: ContentModel): Boolean {
        Log.e("DIFF_CALLBACK", "areItemsTheSame=====>" + TextUtils.equals(oldItem.id, newItem.id))
        return   oldItem.equals(newItem)
    }

    override fun areContentsTheSame(oldItem: ContentModel, newItem: ContentModel): Boolean {
        Log.e(
            "DIFF_CALLBACK",
            "areContentsTheSame=====>" + TextUtils.equals(oldItem.id, newItem.id)
        )
        return TextUtils.equals(oldItem.id, newItem.id)
    }
}

class ContentAdapter(private val fg: Fragment) :
    ListAdapter<ContentModel, ContentViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ContentViewHolder(inflate)
    }

    override fun submitList(list: MutableList<ContentModel>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        when (item.d_type) {
            "0" -> {
                return R.layout.item_one_pic
            }
            "1" -> {
                return R.layout.item_three_pic
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
        holder.bindView(getItem(position), fg,position)
    }
}


class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private fun bindTypeThreePics(
        contentModel: ContentModel,
        fg: Fragment,
        position: Int
    ) {

        val threeImgViews = itemView.findViewById<PicViews>(R.id.ThreeImgViews)
        val threeContentTitleTv = itemView.findViewById<TextView>(R.id.threeContentTitleTv)
        val localConfigModel = LocalRepository.getLocalConfigModel()
        val picArr = contentModel.picArr
        if (picArr.isNotEmpty()) {
            val pic = picArr[0]
            localConfigModel?.let {
                val imgHost = it.getImgHost()
                val urls = mutableListOf(imgHost + pic)
                threeImgViews.setImages(urls)
                itemView.setOnClickListener {
//                    ImagePreviewFragment.start(fg,arrayListOf(imgHost + pic))
                    PermissionHelper.applyPermission(Manifest.permission_group.STORAGE)
//                    ImagePreviewFragment.start(fg,arrayListOf(imgHost + pic))
                }
            }
        }
//        contentModel.text_info.let {
            threeContentTitleTv.text = "${contentModel.text_info}$position"
//        }
    }

    fun bindView(
        contentModel: ContentModel,
        fg: Fragment,
        position: Int
    ) {  when (contentModel.d_type) {
            "1" -> {
                bindTypeThreePics(contentModel, fg,position)
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