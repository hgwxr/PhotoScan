package com.hgwxr.photo.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hgwxr.photo.ui.home.display.DistributeFragment
import com.hgwxr.photo.ui.home.main.MainPageFragment
import com.hgwxr.photo.ui.home.mine.MineFragment
import com.hgwxr.photo.ui.home.picture.PictureFragment


class PageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    companion object {
        const val NUM_PAGES = 4
        const val PAGE_MAIN = 0
        const val PAGE_Distribute = 1
        const val PAGE_Picture = 2
        const val PAGE_MINE = 3

    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            PAGE_MAIN -> {
                MainPageFragment.newInstance()
            }
            PAGE_Distribute -> {
                DistributeFragment.newInstance()
            }
            PAGE_Picture -> {
                PictureFragment.newInstance()
            }
            else -> {
                MineFragment.newInstance()
            }
        }
    }
}