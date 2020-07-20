package com.hgwxr.photo.ui.home.picture

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hgwxr.photo.R

class PictureFragment : Fragment() {

    companion object {
        fun newInstance() = PictureFragment()
    }

    private lateinit var viewModel: PictureViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.picture_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PictureViewModel::class.java)
        // TODO: Use the ViewModel
    }

}