package com.example.testassignment.remote.common

import com.example.testassignment.remote.rest.IRestBuilder

abstract class BaseRemoteApiDataSource<T>(
    val apiBuilder: IRestBuilder
) : BaseRemoteDataSource() {

    protected abstract val hostUrl: String
    protected abstract val apiInterface: Class<T>
    protected abstract val headersMap: Map<String, String>

    private var currentHostUrl: String? = null
    private var currentApi: T? = null
    private var currentDownloadApi: T? = null

    protected val api: T
        get() {
            val shouldRecreate = currentHostUrl != hostUrl || currentApi == null
            return (currentApi?.takeIf { !shouldRecreate } ?: createApi()).also {
                currentHostUrl = hostUrl
                currentApi = it
            }
        }

    protected val downloadApi: T
        get() {
            val shouldRecreate = currentHostUrl != hostUrl || currentDownloadApi == null
            return (currentDownloadApi?.takeIf { !shouldRecreate } ?: createDownloadApi()).also {
                currentHostUrl = hostUrl
                currentDownloadApi = it
            }
        }

    private fun createApi(): T {
        return apiBuilder.build(hostUrl) { requestBuilder ->
            headersMap.forEach {
                requestBuilder.addHeader(it.key, it.value)
            }
        }.create(apiInterface)
    }

    private fun createDownloadApi(): T {
        return apiBuilder.build(hostUrl) {}.create(apiInterface)
    }

    companion object {
        const val NO_CONTENT_CODE = 204
    }
}