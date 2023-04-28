package uz.frankie.tasktest.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import uz.frankie.tasktest.network.models.CardRequest

@Entity(tableName = "cards")
data class Card(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val defaultId: String? = null,
    @SerializedName("card_number")
    val cardNumber: String? = null,
    @SerializedName("expire_date")
    val expireDate: String? = null,
    @SerializedName("card_name")
    val cardName: String? = null,
    @SerializedName("card_balance")
    val cardBalance: String = "0.00",
    var isUploaded: Boolean = true
){
    fun cardToCardRequest(): CardRequest {
        return CardRequest(
            cardNumber = cardNumber,
            expireDate = expireDate,
            cardName = cardName,
            cardBalance = cardBalance
        )
    }
}