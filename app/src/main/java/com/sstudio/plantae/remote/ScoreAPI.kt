package com.sstudio.plantae.remote

import com.sstudio.plantae.model.EvaluationScore
import com.sstudio.plantae.model.Task
import retrofit2.Call
import retrofit2.http.*

interface ScoreAPI {
    @FormUrlEncoded
    @POST("score")
    fun postScore(
        @Field("name") name: String,
        @Field("score") score: Double
    ): Call<EvaluationScore.Meta>

    @GET("score")
    fun getData(): Call<EvaluationScore>

    @GET("score/{name}")
    fun getDataByName(@Path("name") name: String): Call<EvaluationScore>

    @GET("score/del/{id}")
    fun delData(@Path("id") id: Int): Call<EvaluationScore.Meta>
}