package com.hgwxr.photo.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.hgwxr.photo.R

class PhoneLoginFragment : Fragment() {

    companion object {
        fun newInstance() = PhoneLoginFragment()
    }

    private val viewModel: PhoneLoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.phone_login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val phoneNumber = view.findViewById<EditText>(R.id.phoneNum)
        val phoneLayout = view.findViewById<TextInputLayout>(R.id.phoneLayout)
        val btnNextStep = view.findViewById<Button>(R.id.btnNextStep)
        viewModel.buttonEnable.observe(viewLifecycleOwner, Observer {
            btnNextStep.isEnabled = it
        })
        viewModel.loginForm.observe(viewLifecycleOwner, Observer {
            phoneLayout.error = it
        })
        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                //ignore
                viewModel.checkButton(phoneNumber.text.toString())
            }
        }
        phoneNumber.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                doNextStep(phoneNumber.text.toString())
            }
            false
        }
        btnNextStep.setOnClickListener {
            doNextStep(phoneNumber.text.toString())
        }
        phoneNumber.addTextChangedListener(afterTextChangedListener)
    }

    private fun doNextStep(phone: String) {
        viewModel.performNextStep(phone)
        findNavController().navigate(PhoneLoginFragmentDirections.actionPhoneLoginFragmentToLoginFragment())
    }

}