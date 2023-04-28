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
import uz.frankie.tasktest.repositories.CreateCardRepository
import uz.frankie.tasktest.utils.NetworkResource
import uz.frankie.tasktest.viewmodels.state.CreateCardState
import uz.frankie.tasktest.viewmodels.state.MainUiState
import javax.inject.Inject

@HiltViewModel
class CreateCardViewModel @Inject constructor(private val createCardRepository: CreateCardRepository) : ViewModel() {

    private var cardsToSaveJob: Job? = null

    private val _uiState = MutableStateFlow(CreateCardState())
    val uiState = _uiState.asStateFlow()

    fun saveToLocal(card: Card){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when(val result = createCardRepository.saveCardToLocal(card)){
                is NetworkResource.Success ->{
                    _uiState.update { it.copy(card = result.data!!, isLoading = false) }
                }
                is NetworkResource.Error ->{
                    _uiState.update { it.copy(errorMessage = result.uiText.toString(), isLoading = false) }
                }
            }
        }
    }

    fun saveToServer(card: Card){
        cardsToSaveJob?.cancel()
        cardsToSaveJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when(val result = createCardRepository.saveCardToServer(card)){
                is NetworkResource.Success ->{
                    _uiState.update { it.copy(card = result.data!!, isLoading = false) }
                }
                is NetworkResource.Error ->{
                    _uiState.update { it.copy(errorMessage = result.uiText.toString(), isLoading = false) }
                }
            }
        }
    }

}