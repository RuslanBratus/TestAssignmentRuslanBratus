package com.example.testassignment.di.modules

import com.example.testassignment.remote.rest.IRestBuilder
import com.example.testassignment.remote.rest.RestBuilder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RestModule {

    @Binds
    fun bindNetworkModule(restBuilder: RestBuilder): IRestBuilder
}