package uz.frankie.tasktest.viewmodels.state

import uz.frankie.tasktest.models.Card

data class CreateCardState(
    val card: Card? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)