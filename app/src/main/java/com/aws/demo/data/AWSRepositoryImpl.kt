package com.aws.demo.data

import aws.sdk.kotlin.services.location.model.SearchPlaceIndexForSuggestionsRequest
import com.aws.demo.data.model.AwsPlaceType
import com.aws.demo.domain.AWSRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AWSRepositoryImpl @Inject constructor(
    private val awsLocationProvider: AwsLocationProvider
) : AWSRepository {

    override suspend fun getAddressSuggestions(query: String): List<String> {
        return  withContext(Dispatchers.IO) {
            val locationClient = awsLocationProvider.getLocationClient()
            val request = SearchPlaceIndexForSuggestionsRequest {
                text = query
                filterCategories = AwsPlaceType.entries.map { it.name }
                filterCountries = listOf("AUS", "IND")
                indexName = "AWS_PLACE_INDEX"
                language = "en"
                maxResults = 5
            }
            val response = locationClient.searchPlaceIndexForSuggestions(request)
            response.results.map { it.text }
        }
    }
}