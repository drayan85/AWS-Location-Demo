package com.aws.demo.domain.interactor

import com.aws.demo.domain.AWSRepository
import com.aws.demo.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class GetAwsLocationSuggestionUseCase @Inject constructor(
    private val awsRepository: AWSRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<String>>> = channelFlow {
       try {
           trySend(Resource.Loading())
           val suggestions = awsRepository.getAddressSuggestions(query)
           trySend(Resource.Success(suggestions))
       } catch (e: Exception) {
           trySend(Resource.Error(e))
       }
    }
}