package com.aws.demo.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aws.demo.domain.interactor.GetAwsLocationSuggestionUseCase
import com.aws.demo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAwsLocationSuggestionUseCase: GetAwsLocationSuggestionUseCase
) : ViewModel() {

    var effects = MutableSharedFlow<MainViewModelEffect>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )
        private set

    fun getAddressSuggestions(query: String) {
        viewModelScope.launch {
            getAwsLocationSuggestionUseCase(query).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Show loading
                    }
                    is Resource.Success -> {
                        resource.data?.let {
                            effects.emit(MainViewModelEffect.DisplaySuggestions(it))
                        }

                    }
                    is Resource.Error -> {
                        effects.emit(MainViewModelEffect.DisplayError(resource.cause))
                    }
                }

            }
        }
    }
}


sealed class MainViewModelEffect {
    data class DisplayError(val error: Throwable) : MainViewModelEffect()
    data class DisplaySuggestions(val suggestions: List<String>) : MainViewModelEffect()
}