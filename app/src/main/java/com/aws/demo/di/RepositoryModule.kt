package com.aws.demo.di

import com.aws.demo.data.AWSRepositoryImpl
import com.aws.demo.domain.AWSRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun provideAWSRepository(repo: AWSRepositoryImpl): AWSRepository
}