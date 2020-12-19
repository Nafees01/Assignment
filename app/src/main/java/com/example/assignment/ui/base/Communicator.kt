package com.example.assignment.ui.base

import androidx.fragment.app.Fragment

interface Communicator {
    fun showToast(msg: String)
    fun showLoader()
    fun hideLoader()
    fun setContentFragment(fragment: Fragment?, flag: Boolean)
}