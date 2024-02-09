package com.example.testassignment.remote.rest

import android.content.Context
import com.example.testassignment.data.error.Exceptions
import com.example.testassignment.extensions.fromJson
import com.example.testassignment.remote.dto.ErrorsResponseDTO
import com.example.testassignment.remote.dto.ServerErrorResponseDTO
import com.example.testassignment.remote.interceptors.NetworkConnectionInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestBuilder @Inject constructor(
    @ApplicationContext private val context: Context
): IRestBuilder {

    private val gson by lazy {
        GsonBuilder()
            .serializeNulls()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    private val errorHandlingInterceptor by lazy {
        object : Interceptor {

            override fun intercept(chain: Interceptor.Chain): Response {
                val response = chain.proceed(chain.request().newBuilder().build())
                /* Throwing an exception if request wasn't successful.
                This allows us to handle all server errors in common way without
                writing a lot of boilerplate code.
                For example all non 200 codes will be passed to the onFailure method of Retrofit callback
                */
                if (!response.isSuccessful) {
                    val errorDto = ErrorsResponseDTO(
                        response.code,
                        parseServerErrorResponse(response)
                    )
                    throw Exceptions.NetworkRequestException(errorDto)
                } else if (response.body == null) {
                    throw Exceptions.NoResponseContentException()
                }
                return response
            }
        }
    }

    override fun build(
        baseUrl: String,
        requestBuilder: (builder: Request.Builder) -> Unit
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(baseUrl)
            .client(getOkHttpClient(requestBuilder))
            .build()

    private fun getOkHttpClient(requestBuilder: (builder: Request.Builder) -> Unit): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                request.addHeader(CONTENT_TYPE_HEADER, CONTENT_TYPE_HEADER_VALUE)
                requestBuilder(request)
                chain.proceed(request.build())
            }.also {
                /*
                    Interceptor should not be used when testing progress of uploading/downloading files via retrofit.
                 */
                if (com.example.testassignment.BuildConfig.ENABLE_LOGS) {
                    it.addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }
            .addInterceptor(errorHandlingInterceptor)
            .addInterceptor(NetworkConnectionInterceptor(context))
            .build()

    private fun parseServerErrorResponse(response: Response): List<ServerErrorResponseDTO> =
        try {
            listOf(Gson().fromJson(response.body?.string() ?: ""))
        } catch (e: Exception) {
            listOf()
        }

    companion object {

        private const val CONNECTION_TIMEOUT = 10L
        private const val WRITE_TIMEOUT = 20L
        private const val READ_TIMEOUT = 30L

        const val CONTENT_TYPE_HEADER = "Content-Type"
        const val CONTENT_TYPE_HEADER_VALUE = "application/json"
    }
}