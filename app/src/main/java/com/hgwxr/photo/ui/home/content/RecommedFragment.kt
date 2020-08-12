package com.hgwxr.photo.ui.home.content

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hgwxr.photo.R
import com.hgwxr.photo.ui.home.content.adapter.RecommedAdapter
import com.hgwxr.photo.utils.ToastUtils
import com.scwang.smart.refresh.header.MaterialHeader
import kotlinx.android.synthetic.main.fragment_recommed.*
import kotlinx.coroutines.*
import java.lang.Exception


class RecommedFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val mViewModel: RecommedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recommedAdapter = RecommedAdapter(this)
        refreshContent.setRefreshHeader(MaterialHeader(context))
        refreshContent.setOnRefreshListener {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val contentModels = mViewModel.loadRecommed()
                    Log.e("contentModels:", contentModels.toString())
                    recommedAdapter.submitList(contentModels) {
                        ToastUtils.showToast("刷新成功!")
                    }
                } catch (e: Exception) {
                    e.message?.let { it1 -> ToastUtils.showToast(it1) }
                }
                it.finishRefresh()
            }
        }
        recommendContent.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recommedAdapter
            val dividerItemDecoration =
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(context, R.drawable.bg_divider_f5_5)?.let {
                dividerItemDecoration.setDrawable(
                    it
                )
            }
            addItemDecoration(dividerItemDecoration)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = RecommedFragment()
    }
}