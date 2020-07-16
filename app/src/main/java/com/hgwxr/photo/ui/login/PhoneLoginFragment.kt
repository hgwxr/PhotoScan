package com.hgwxr.photo.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.createGraph
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.google.android.material.textfield.TextInputLayout
import com.hgwxr.photo.R
import com.hgwxr.photo.utils.ToastUtils
import com.hgwxr.photo.utils.snackBar
import kotlinx.android.synthetic.main.phone_login_fragment.*

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

//        val phoneNumber = view.findViewById<EditText>(R.id.phoneNum)
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
                doNextStep()
            }
            false
        }
        btnNextStep.setOnClickListener {
            doNextStep()
        }
        phoneNumber.addTextChangedListener(afterTextChangedListener)
        viewModel.processBar.observe(viewLifecycleOwner, Observer {
            if (!it.init) {
                return@Observer
            }
            if (it.success) {
                btnGetCheckCode.startCheckCode()
//                btnGetCheckCode.snackBar(it.message)
            } else {
                btnGetCheckCode.snackBar(it.message).show()
            }
        })
        viewModel.clickNextStep.observe(viewLifecycleOwner, Observer {
            if (it) {
                getCheckCodeGroup.visibility = View.VISIBLE
            } else {
                getCheckCodeGroup.visibility = View.GONE
            }
            if (btnNextStep.layoutParams is ConstraintLayout.LayoutParams) {
                (btnNextStep.layoutParams as ConstraintLayout.LayoutParams).topToBottom =
                    if (it) R.id.dividerLine1 else R.id.dividerLine
            }
        })
        closeBack.setOnClickListener {
            findNavController().popBackStack().takeIf {
                !viewModel.clickBack()
            }
        }
        btnGetCheckCode.setOnClickListener {
            phoneNumber.isEnabled = false
            context?.let { it1 ->
                val phone = phoneNumber.text.toString()
                val dialog = MaterialDialog(it1).title(R.string.str_title_check_phone)
                    .message(
                        text = getString(
                            R.string.str_title_check_phone_content,
                            phoneNumber.text.toString()
                        )
                    )
                dialog.cancelOnTouchOutside(false)
                dialog
                    .onDismiss {
                        phoneNumber.isEnabled = true
                    }
                    .show {
                        positiveButton(R.string.str_dialog_btn_cancel) { dialog ->
                            // Do something
                            dialog.dismiss()
                        }
                        negativeButton(R.string.str_dialog_btn_ok) { dialog ->
                            // Do something
                            dialog.dismiss()
                            Log.e(
                                "negativeButton:",
                                this@PhoneLoginFragment.phoneNumber.text.toString() + "   " + phone
                            )
                            it.snackBar(this@PhoneLoginFragment.phoneNumber.text.toString())
                            this@PhoneLoginFragment.viewModel.performSendCheckCode(phone)
                        }
                    }
            }
        }
        viewModel.loginState.observe(viewLifecycleOwner, Observer {
            if (it.init && it.success) {
                findNavController()
                findNavController().setGraph(R.navigation.nav_graph)
                ToastUtils.showToast(it.message)
            } else {
                view.snackBar(it.message)
            }
        })

    }

    private fun doNextStep() {
        viewModel.performNextStep(phoneNumber.text.toString(), checkCode.text.toString())
//        findNavController().navigate(PhoneLoginFragmentDirections.actionPhoneLoginFragmentToLoginFragment())
    }

}