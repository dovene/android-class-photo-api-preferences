package com.dovene.androidhttprequestsharedprefences

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


class PhotoApi {
    interface PhotoService {
        // For testing https://api.pexels.com/v1/search?query=people
        @GET("search")
        fun getPhotos(@Header("Authorization") authorization:String, @Query("query") criteria: String): Call<PhotoApiResponse>
    }

    private val baseUrl = "https://api.pexels.com/v1/"

    fun getClient(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}