package com.hgwxr.photo.ui.home.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.hgwxr.photo.R
import com.hgwxr.photo.ui.home.content.ContentsFragment
import com.hgwxr.photo.ui.home.content.RecommedFragment
import kotlinx.android.synthetic.main.main_page_fragment.*
import kotlinx.coroutines.*

class MainPageFragment : Fragment() {

    companion object {
        fun newInstance() = MainPageFragment()
    }

    private val viewModel: MainPageViewModel by activityViewModels()

    var userVisible = false

    override fun onResume() {
        super.onResume()
        userVisible = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_page_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            val mainPagerAdapter = MainPagerAdapter(
                it, mutableListOf(
                    RecommedFragment.newInstance(),
//                    ContentsFragment.newInstance(ContentsFragment.TYPE_INTRODUCE),
                    ContentsFragment.newInstance(ContentsFragment.TYPE_VIDEO),
                    ContentsFragment.newInstance(ContentsFragment.TYPE_PICTURE)
                )
            )
            mainPageContainer.adapter = mainPagerAdapter
            TabLayoutMediator(mainTabLayout, mainPageContainer) { tab, position ->
                tab.text = ContentsFragment.getTitleByType(position)
            }.attach()
            val mutableListOf = mutableListOf<String>("文本1", "文本1", "文本2", "文本3", "文本4", "文本5")
            GlobalScope.launch(Dispatchers.Main) {
                textSwitcher.startDefault()
//                withContext(Dispatchers.Default) {
//                    while (true) {
//                        mutableListOf.forEach { s ->
//                            withContext(Dispatchers.Main) {
//                                textSwitcher.setText(s)
////                                textSwitcher.showNext()
//                            }
//                            delay(2000)
//                        }
//                    }
//                }
            }
        }
    }


}