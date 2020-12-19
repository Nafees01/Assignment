package com.example.assignment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.assignment.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    private val observerTimer = object : DisposableObserver<Long>() {
        override fun onComplete() {}

        override fun onNext(t: Long) {
            loginScreen()
        }

        override fun onError(e: Throwable) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //  CUSTOM_BUILD_TYPE = BuildType.DEBUG.BUILD_TYPE
    }

    override fun onResume() {
        super.onResume()
        Observable.timer(1L, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observerTimer)
    }

    override fun onPause() {
        super.onPause()
        if (!observerTimer.isDisposed) {
            observerTimer.dispose()
        }
    }

    private fun loginScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit)
    }
}