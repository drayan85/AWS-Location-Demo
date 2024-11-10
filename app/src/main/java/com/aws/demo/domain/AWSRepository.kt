package com.aws.demo.domain

interface AWSRepository {

    suspend fun getAddressSuggestions(query: String): List<String>
}