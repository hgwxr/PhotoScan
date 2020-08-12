package com.hgwxr.photo.widgets

import android.content.Context
import android.database.DataSetObserver
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout


class ImageContentLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var adapter: BaseAdapter? = null
    private var mObserver: DataSetObserver? = null

    fun setAdapter(adapter: BaseAdapter) {
        this.adapter = adapter
        this.mObserver = object : DataSetObserver() {
            override fun onChanged() {
                super.onChanged()
            }
        }
        this.adapter!!.registerDataSetObserver(this.mObserver)
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        this.adapter?.let {
            val widthMode = MeasureSpec.getMode(widthMeasureSpec)
            val heightMode = MeasureSpec.getMode(heightMeasureSpec)
            var widthSize = MeasureSpec.getSize(widthMeasureSpec)
            var heightSize = MeasureSpec.getSize(heightMeasureSpec)

            when (heightMode) {
                MeasureSpec.AT_MOST -> {
                    val count = it.count

                }
                else -> {
                }
            }
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

    }
}
//
//class SimpleMAdapter : BaseAdapter() {
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//
//    }
//
//    override fun getItem(position: Int): Any {
//    }
//
//    override fun getItemId(position: Int): Long {
//    }
//
//    override fun getCount(): Int {
//    }
//
//
//}
