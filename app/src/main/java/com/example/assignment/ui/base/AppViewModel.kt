package com.example.assignment.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.repository.Repository

open class AppViewModel : ViewModel() {
    val toastMessage by lazy { MutableLiveData<String>() }
    val showLoader by lazy { MutableLiveData<Boolean>() }

    open fun getRepository(): Repository {
        return Repository()
    }

}