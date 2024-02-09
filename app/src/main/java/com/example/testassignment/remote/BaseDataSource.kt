package com.example.testassignment.remote

import com.example.testassignment.BuildConfig
import com.example.testassignment.remote.common.BaseRemoteApiDataSource
import com.example.testassignment.remote.rest.IRestBuilder

abstract class BaseDataSource<T>(
    apiBuilder: IRestBuilder
) : BaseRemoteApiDataSource<T>(apiBuilder) {

    override val hostUrl: String
        get() = BuildConfig.HOST

    override val headersMap: Map<String, String>
        get() = mutableMapOf<String, String>()


    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val AUTHORIZATION_BASIC = "Basic"
        const val AUTHORIZATION_BEARER = "Bearer"
    }
}
