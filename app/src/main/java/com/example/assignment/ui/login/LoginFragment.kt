package com.example.assignment.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.assignment.model.requestModel.LoginRequestModel

import com.example.assignment.R
import com.example.assignment.ui.base.BaseFragment
import com.example.assignment.ui.inputForm.InputFormFragment
import com.example.assignment.utils.AppHelper

import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : BaseFragment() {

    private lateinit var currentViewModel: LoginViewModel
    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun addViewModel() {
        currentViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel = currentViewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogin.setOnClickListener {
            validateData()
        }

        observer()
    }

    private fun observer() {
        currentViewModel.token.observe(activity!!, {
            if (!it.isNullOrEmpty())
               saveToken(it)
        })
    }

    private fun saveToken(s: String) {
        AppHelper.token = "Token $s"
        communicator?.setContentFragment(InputFormFragment(),false)
    }

    private fun validateData() {
        if(etUserName.text.isNullOrEmpty()){
            communicator?.showToast("User Name is required!")
        } else if(etPassword.text.isNullOrEmpty()){
           communicator?.showToast("Password is required!")
        } else{
            val loginRequestModel = LoginRequestModel()
            loginRequestModel.username = etUserName.text.toString()
            loginRequestModel.password = etPassword.text.toString()
            currentViewModel.requestForLogin(loginRequestModel)
        }

    }


}