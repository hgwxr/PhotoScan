package com.hgwxr.photo.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.hgwxr.photo.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.main_page_fragment.*


class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            viewPager.adapter = PageAdapter(it)
            viewPager.run {
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        bottomNavigation.menu.getItem(position).isChecked = true
                    }
                })
            }
            viewPager.isUserInputEnabled = false
            bottomNavigation.run {
                setOnNavigationItemSelectedListener { item: MenuItem ->
                    when (item.itemId) {
                        R.id.mainPage -> {
                            viewPager.setCurrentItem(PageAdapter.PAGE_MAIN, true)
                        }
                        R.id.distribute -> {
                            viewPager.setCurrentItem(PageAdapter.PAGE_Distribute, true)
                        }
                        R.id.picture -> {
                            viewPager.setCurrentItem(PageAdapter.PAGE_Picture, true)
                        }
                        else -> {
                            viewPager.setCurrentItem(PageAdapter.PAGE_MINE, true)
                        }
                    }
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}