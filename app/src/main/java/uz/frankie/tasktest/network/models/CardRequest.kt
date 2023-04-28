package uz.frankie.tasktest.network.models

import com.google.gson.annotations.SerializedName
import uz.frankie.tasktest.models.Card

data class CardRequest(
    @SerializedName("card_number")
    val cardNumber: String? = null,
    @SerializedName("expire_date")
    val expireDate: String? = null,
    @SerializedName("card_name")
    val cardName: String? = null,
    @SerializedName("card_balance")
    val cardBalance: String? = null,
)