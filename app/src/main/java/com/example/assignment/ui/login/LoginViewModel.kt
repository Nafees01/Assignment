package com.example.assignment.ui.login

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.example.assignment.model.requestModel.LoginRequestModel
import com.example.assignment.model.responseModel.LoginResponse
import com.example.assignment.ui.base.AppViewModel
import com.example.assignment.utils.AppHelper
import io.reactivex.observers.DisposableObserver


class LoginViewModel : AppViewModel() {

    val token by lazy { MutableLiveData<String>() }

    @SuppressLint("CheckResult")
    fun requestForLogin(loginRequestModel: LoginRequestModel) {
        getRepository().requestForLogin(loginRequestModel)
            ?.doOnSubscribe { showLoader.value = true }
            ?.doAfterTerminate { showLoader.value = false }
            ?.subscribeWith(object : DisposableObserver<LoginResponse>() {
                override fun onNext(t: LoginResponse) {
                    if (t.success) {
                        toastMessage.value = "login Successful!"
                        token.value = t.token
                    } else {
                        toastMessage.value = t.message
                    }
                }


                override fun onError(e: Throwable) {

                    toastMessage.value = "Credential is not correct!"
                }


                override fun onComplete() {

                }


            })

    }


}