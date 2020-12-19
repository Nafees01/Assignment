package com.example.assignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.assignment.R
import com.example.assignment.ui.base.Communicator
import com.example.assignment.ui.login.LoginFragment
import dmax.dialog.SpotsDialog
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() , Communicator {

    var dialog: android.app.AlertDialog by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentFragment(LoginFragment(),false)
        dialog = SpotsDialog.Builder().setContext(this).setTheme(R.style.LoadingDialog).build()
        dialog.setCancelable(false)
    }


     override fun setContentFragment(fragment: Fragment?, addToBackStack: Boolean) {
         val supportedFragmentManager = supportFragmentManager
         val fragmentManager = supportedFragmentManager.beginTransaction()

         fragmentManager.setCustomAnimations(
             R.anim.activity_enter,
             R.anim.activity_exit,
             R.anim.activity_enter,
             R.anim.activity_exit
         )
         if (addToBackStack)
             if (fragment != null) {
                 fragmentManager.addToBackStack(fragment.javaClass.name)
             }

         if (fragment != null) {
             fragmentManager.replace(R.id.container, fragment)
                 .commit()
         }
    }

     override fun showLoader() {
        runOnUiThread { if (!dialog.isShowing) dialog.show() }
    }

     override fun hideLoader() {
        runOnUiThread { if (dialog.isShowing) dialog.dismiss() }
    }



    override fun showToast(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}