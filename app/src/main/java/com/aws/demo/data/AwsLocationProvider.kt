package com.aws.demo.data

import android.content.Context
import aws.sdk.kotlin.services.location.LocationClient
import aws.sdk.kotlin.services.location.withConfig
import aws.smithy.kotlin.runtime.http.engine.okhttp4.OkHttp4Engine
import dagger.hilt.android.qualifiers.ApplicationContext
import software.amazon.location.auth.AuthHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AwsLocationProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var locationClient: LocationClient? = null

    suspend fun getLocationClient(): LocationClient {
        return runCatching {
            if (locationClient == null) {
                val authHelper = AuthHelper.withCognitoIdentityPool(
                    "AWS_COGNITO_POOL_ID",
                    context
                )

                locationClient = LocationClient(authHelper.getLocationClientConfig())

                locationClient!!.withConfig {
                    httpClient = OkHttp4Engine()
                }
            }
            locationClient!!
        }.getOrElse { e ->
            e.printStackTrace()
            error("COGNITO authentication failed")
        }
    }
}