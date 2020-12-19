package com.example.assignment.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.plugins.RxJavaPlugins
import java.io.IOException
import java.net.SocketException
import kotlin.properties.Delegates


open class BaseFragment : Fragment() {
    var communicator: Communicator? = null
    protected var viewModel: AppViewModel by Delegates.notNull()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*RxJavaPlugins.setErrorHandler { e: Throwable? ->
            if (e is IOException || e is SocketException) {
                // handle exception
                return@setErrorHandler
            }
        }*/

    }
   open fun addViewModel(){

   }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Communicator)
            communicator = context
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseObserver()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addViewModel()
    }
    private fun baseObserver() {
        viewModel.showLoader.observe(activity!!, {
            if (it) communicator?.showLoader() else communicator?.hideLoader()
        })

        viewModel.toastMessage.observe(activity!!, {
            if (!it.isNullOrEmpty())
                communicator?.showToast(it)
        })

    }


}