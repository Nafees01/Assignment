package com.example.assignment.repository

import com.example.assignment.model.requestModel.CvFileModel
import com.example.assignment.model.requestModel.InputInfoRequestModel
import com.example.assignment.model.requestModel.LoginRequestModel
import com.example.assignment.model.responseModel.LoginResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.example.assignment.network.ApiService as ApiService

class Repository {

    private fun getRetrofit(): ApiService {
        val interceptor =  HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        var client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://recruitment.fisdev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        
        return retrofit.create(ApiService::class.java)
    }

    fun requestForLogin( loginRequestModel: LoginRequestModel): Observable<LoginResponse>? {
       return getRetrofit().requestForLogin(loginRequestModel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun requestForSubmittingInputInfo(token: String, inputInfoRequestModel: InputInfoRequestModel): Observable<InputInfoRequestModel>? {
       return getRetrofit().requestForSubmittingInputInfo(token,inputInfoRequestModel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
    fun requestForSubmittingCv(token: String, cvFileModel: CvFileModel): Observable<InputInfoRequestModel>? {
       return getRetrofit().requestForSubmittingCv(token,cvFileModel.id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}