package com.sstudio.plantae.remote

import com.sstudio.plantae.model.Meta
import com.sstudio.plantae.model.Task
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface IUploadAPI {
    @Multipart
    @POST("task")
    fun uploadFile(
//        @Part("file") file: RequestBody,
        @Part name: MultipartBody.Part,
        @Part("name") namePart: RequestBody
//        @Path("name") name: String
//        @PartMap params: Map<String,RequestBody>
    ): Call<Meta>

    @GET("task")
    fun getData(): Call<Task>

    @GET("task/{name}")
    fun getDataByName(@Path("name") name: String): Call<Task>

    @FormUrlEncoded
    @POST("task/del")
    fun delData(@Field("id") id: Int): Call<Task.Meta>
}