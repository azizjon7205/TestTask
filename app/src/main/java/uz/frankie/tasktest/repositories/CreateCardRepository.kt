package uz.frankie.tasktest.repositories

import android.util.Log
import retrofit2.HttpException
import uz.frankie.tasktest.local.CardDao
import uz.frankie.tasktest.models.Card
import uz.frankie.tasktest.network.models.CardResponse
import uz.frankie.tasktest.network.services.CardService
import uz.frankie.tasktest.utils.NetworkResource
import uz.frankie.tasktest.utils.UiText
import java.io.IOException
import javax.inject.Inject

class CreateCardRepository @Inject constructor(
    private val cardService: CardService,
    private val cardDao: CardDao
) {

    suspend fun saveCardToLocal(card: Card): NetworkResource<Card> {
        return try {
            cardDao.insertCard(card)
            NetworkResource.Success(card)
        } catch (e: HttpException) {
            NetworkResource.Error(UiText.StaticString())
        } catch (e: IOException) {
            NetworkResource.Error(UiText.StaticString())
        }
    }

    suspend fun saveCardToServer(card: Card): NetworkResource<Card> {
        return try {
            val cardRequest = card.cardToCardRequest()
            val newCard = cardService.saveCard(cardRequest)
            if (newCard.isSuccessful) {
                saveCardToLocal(newCard.body()?.toCardDto()!!)
//                NetworkResource.Success(newCard.body()?.toCardDto())
            } else {
                NetworkResource.Error(UiText.StaticString())
            }
        } catch (e: HttpException) {
            NetworkResource.Error(UiText.StaticString())
        } catch (e: IOException) {
            NetworkResource.Error(UiText.StaticString())
        }
    }

}