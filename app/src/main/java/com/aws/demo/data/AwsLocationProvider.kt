package com.aws.demo.data

import android.content.Context
import aws.sdk.kotlin.services.location.LocationClient
import aws.sdk.kotlin.services.location.withConfig
import aws.smithy.kotlin.runtime.http.engine.okhttp4.OkHttp4Engine
import dagger.hilt.android.qualifiers.ApplicationContext
import software.amazon.location.auth.AuthHelper
import software.amazon.location.auth.LocationCredentialsProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AwsLocationProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var locationCredentialsProvider: LocationCredentialsProvider? = null

    suspend fun getLocationClient(): LocationClient {
        return runCatching {
            if (locationCredentialsProvider == null) {
                locationCredentialsProvider = AuthHelper(context).authenticateWithCognitoIdentityPool("AWS_COGNITO_POOL_ID")
            }
            val locationClient = locationCredentialsProvider?.getLocationClient()
                ?: error("AWS:CLIENT Location Client Retrieval failed")
            locationClient.withConfig {
                httpClient = OkHttp4Engine()
            }
        }.getOrElse { error ->
            if (error is IllegalStateException) {
                locationCredentialsProvider?.refresh()
            }

            error("AWS:COGNITO authentication failed")
        }
    }
}