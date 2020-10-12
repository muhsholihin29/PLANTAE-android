package com.sstudio.plantae.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import com.sstudio.plantae.BuildConfig.BASE_URL

object RetrofitClient {

    private var retrofitClient: Retrofit? = null

    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    fun getClient(): Retrofit {
        if (retrofitClient == null) {
            retrofitClient = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofitClient as Retrofit
    }
}