package com.hgwxr.photo.ui.home.content

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.hgwxr.photo.PApplication
import com.hgwxr.photo.R
import com.hgwxr.photo.data.model.ContentModel
import com.hgwxr.photo.ui.home.content.adapter.ContentAdapter
import com.hgwxr.photo.utils.ToastUtils
import com.hgwxr.photo.utils.snackBar
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.header.FalsifyFooter
import com.scwang.smart.refresh.header.FalsifyHeader
import com.scwang.smart.refresh.header.MaterialHeader
import kotlinx.android.synthetic.main.contents_fragment.*

class ContentsFragment : Fragment() {

    companion object {
        const val TYPE_INTRODUCE = 0
        const val TYPE_KEY = "ketType"
        const val TYPE_VIDEO = 1
        const val TYPE_PICTURE = 2
        fun newInstance(type: Int = TYPE_INTRODUCE) = ContentsFragment().apply {
            arguments = Bundle().apply {
                putInt(TYPE_KEY, type)
            }
        }

        fun getTitleByType(type: Int): String? {
            return when (type) {
                TYPE_INTRODUCE -> {
                    PApplication.getContext().getString(R.string.str_title_introduce)
                }
                TYPE_VIDEO -> {
                    PApplication.getContext().getString(R.string.str_title_video)
                }
                TYPE_PICTURE -> {
                    PApplication.getContext().getString(R.string.str_tiitle_picture)
                }
                else -> {
                    PApplication.getContext().getString(R.string.str_title_else)
                }
            }
        }
    }

    private val viewModel: ContentsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contents_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val type = arguments?.getInt(TYPE_KEY)
        type?.let {
            viewModel.setType(it)
//            contentText.text = "type:$type"
            viewModel.performLoadData()
//            refreshLayout.setRefreshHeader(FalsifyHeader(context))
            val classicsHeader = ClassicsHeader(context)
            refreshLayout.setRefreshHeader(classicsHeader)
            val classicsFooter = ClassicsFooter(context)
            refreshLayout.setRefreshFooter(classicsFooter)
            refreshLayout.setOnRefreshListener {
                viewModel.performLoadData()
            }
            refreshLayout.setOnLoadMoreListener {
                viewModel.loadNextRecommend()
                ToastUtils.showToast("load next")

            }
            contentRv.apply {
                layoutManager = LinearLayoutManager(context)
                val contentAdapter = ContentAdapter(this@ContentsFragment)
                adapter = contentAdapter
//                val pagerSnapHelper = PagerSnapHelper()
//                pagerSnapHelper.attachToRecyclerView(this)
                viewModel.loadNext.observe(viewLifecycleOwner, Observer { v ->
//                    if (v.isInit()) {
//                        refreshLayout.finishRefresh()
//                    } else {
                        refreshLayout.finishLoadMore()
                        when {
                            v.over() -> {
                                classicsFooter.setNoMoreData(true)
                            }
                            v.error() -> {
                                //                        classicsFooter.onStateChanged()
                            }
                            v.success() -> {

                            }
                        }
                })
                viewModel.mainListForm.observe(viewLifecycleOwner, Observer { result ->
                    if (viewModel.isMainPage()) {
                        loadingProcessGroup.visibility = View.GONE
                        contentAdapter.submitList(result.contentList.toMutableList())
                        view.snackBar("加载成功:" + result.contentList.size).show()
                    } else if (viewModel.isVideoPage()) {

                    } else if (viewModel.isPicPage()) {

                    }
                })
            }

        }
    }

}