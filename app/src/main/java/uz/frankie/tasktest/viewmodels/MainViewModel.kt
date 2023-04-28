package uz.frankie.tasktest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.frankie.tasktest.models.Card
import uz.frankie.tasktest.repositories.CardRepository
import uz.frankie.tasktest.utils.NetworkResource
import uz.frankie.tasktest.viewmodels.state.MainUiState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val cardRepository: CardRepository) : ViewModel() {

    private var cardsJob: Job? = null

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    fun getAllCardsList(){
        cardsJob?.cancel()
        cardsJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when(val result = cardRepository.getAllCards()){
                is NetworkResource.Success ->{
                    _uiState.update { it.copy(cardList = result.data!!, isLoading = false) }
                }
                is NetworkResource.Error ->{
                    _uiState.update { it.copy(errorMessage = result.uiText.toString(), isLoading = false) }
                }
            }
        }
    }

    fun refreshAllCardsList(){
        cardsJob?.cancel()
        cardsJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when(val result = cardRepository.refreshAllCards()){
                is NetworkResource.Success ->{
                    getAllCardsList()
//                    _uiState.update { it.copy(cardList = result.data!!, isLoading = false) }
                }
                is NetworkResource.Error ->{
                    _uiState.update { it.copy(errorMessage = result.uiText.toString(), isLoading = false) }
                }
            }
        }
    }

}