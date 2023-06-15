package com.example.capstone_idoeat.retrofit

import com.example.capstone_idoeat.model.Rekomendasi
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {


//    @FormUrlEncoded
//    @POST("register")
//    fun postRegister(
//        @Field("name") name: String,
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): Call<RegisterResponse>

    @POST("predict")
    fun getRekomendasi(): Call<ArrayList<Rekomendasi>>

//    @POST("predict")
//    fun getRekomendasi(): Call<Rekomendasi>
}