package com.example.testassignment.remote.rest

import okhttp3.Request
import retrofit2.Retrofit

interface IRestBuilder {

    fun build(baseUrl: String,requestBuilder: (builder: Request.Builder) -> Unit): Retrofit
}