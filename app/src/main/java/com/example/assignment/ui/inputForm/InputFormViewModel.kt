package com.example.assignment.ui.inputForm

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.example.assignment.model.requestModel.CvFileModel
import com.example.assignment.model.requestModel.InputInfoRequestModel
import com.example.assignment.ui.base.AppViewModel
import io.reactivex.observers.DisposableObserver


class InputFormViewModel : AppViewModel() {

    val inputInfoResponse by lazy { MutableLiveData<InputInfoRequestModel>() }

    @SuppressLint("CheckResult")
    fun requestForSubmittingInputInfo(
        token: String,
        inputInfoRequestModel: InputInfoRequestModel
    ) {
        getRepository().requestForSubmittingInputInfo(token, inputInfoRequestModel)
            ?.doOnSubscribe { showLoader.value = true }
            ?.doAfterTerminate { showLoader.value = false }
            ?.subscribeWith(object : DisposableObserver<InputInfoRequestModel>() {
                override fun onNext(t: InputInfoRequestModel) {
                    inputInfoResponse.value = t
                    if (!t.message.isNullOrEmpty())
                        toastMessage.value = t.message
                }

                override fun onError(e: Throwable) {
                    toastMessage.value = "something went wrong!"
                }

                override fun onComplete() {}

            })

    }

    @SuppressLint("CheckResult")
    fun requestForSubmittingCv(
        token: String,
        cvRequestModel: CvFileModel
    ) {
        getRepository().requestForSubmittingCv(token, cvRequestModel)
            ?.doOnSubscribe { showLoader.value = true }
            ?.doAfterTerminate { showLoader.value = false }
            ?.subscribeWith(object : DisposableObserver<InputInfoRequestModel>() {
                override fun onNext(t: InputInfoRequestModel) {
                    inputInfoResponse.value = t
                    if (!t.message.isNullOrEmpty())
                        toastMessage.value = t.message
                }

                override fun onError(e: Throwable) {
                    toastMessage.value = "something went wrong!"
                }

                override fun onComplete() {}

            })

    }


}