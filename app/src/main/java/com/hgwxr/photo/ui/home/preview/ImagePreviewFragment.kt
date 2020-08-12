package com.hgwxr.photo.ui.home.preview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.davemorrissey.labs.subscaleview.ImageSource
import com.hgwxr.photo.R
import com.hgwxr.photo.utils.GlideApp
import kotlinx.android.synthetic.main.fragment_image_preview.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_URL = "imageUrls"

class ImagePreviewFragment : Fragment() {
    private lateinit var imageUrls: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageUrls = it.getStringArrayList(ARG_URL)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (imageUrls.isNotEmpty()) {
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                val get = GlideApp.with(this@ImagePreviewFragment).downloadOnly().load(imageUrls[0]).submit().get()
                withContext(Dispatchers.Main){
                    subScaleView.setImage(ImageSource.uri(get.absolutePath))
                    subScaleView.setOnClickListener {
                        findNavController().popBackStack()
                    }
                }

            }
        }
        }
    }

    companion object {
        @JvmStatic
        fun startPreview(param1: ArrayList<String>) =
            ImagePreviewFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_URL, param1)
                }
            }
        fun start(fg:Fragment,param1: ArrayList<String>){
            val bundle = Bundle()
            bundle.putStringArrayList(ARG_URL,param1)
            fg.findNavController().navigate(
                R.id.imagePreviewFragment
                , bundle
            )
        }
    }
}