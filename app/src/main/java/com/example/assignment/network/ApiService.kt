package com.example.assignment.network

import com.example.assignment.model.requestModel.CvFileModel
import com.example.assignment.model.requestModel.InputInfoRequestModel
import com.example.assignment.model.requestModel.LoginRequestModel
import com.example.assignment.model.responseModel.LoginResponse
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {
    @POST("login/")
    fun requestForLogin(@Body loginRequestModel: LoginRequestModel) : Observable<LoginResponse>

    @POST("v1/recruiting-entities/")
    fun requestForSubmittingInputInfo(@Header("Authorization") token : String,@Body inputInfoRequestModel: InputInfoRequestModel) : Observable<InputInfoRequestModel>

    @PUT("file-object/{FILE_TOKEN_ID}/")
    fun requestForSubmittingCv(@Header("Authorization") token: String, @Path("FILE_TOKEN_ID") FILE_TOKEN_ID : Int) : Observable<InputInfoRequestModel>
}