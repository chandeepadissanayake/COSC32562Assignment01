package com.example.myappemp.services

import com.example.myappemp.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersService {

    @GET("/users/{id}")
    fun getUser(@Path("id") id: String): Call<User>

}