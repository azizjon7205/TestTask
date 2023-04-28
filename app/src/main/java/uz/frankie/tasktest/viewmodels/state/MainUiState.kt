package uz.frankie.tasktest.viewmodels.state

import uz.frankie.tasktest.models.Card

data class MainUiState(
    val cardList: List<Card> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)