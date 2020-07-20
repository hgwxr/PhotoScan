package com.hgwxr.photo.ui.home.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainPagerAdapter(fa: FragmentActivity,  fs: MutableList<Fragment>?) :
    FragmentStateAdapter(fa) {
    var fragments = mutableListOf<Fragment>()

    init {
        fs?.let {
            fragments.addAll(it)
        }
    }

    fun addFragment(fragment: Fragment, position: Int = -1) {
        if (position == -1) {
            val start = fragments.size
            fragments.add(fragment)
            notifyItemRangeChanged(start, start + 1)
        } else {
            fragments.add(position, fragment)
            notifyItemRangeInserted(position, 1)
        }
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}