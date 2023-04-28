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

class CardRepository @Inject constructor(
    private val cardService: CardService,
    private val cardDao: CardDao
) {

    suspend fun getAllCards(): NetworkResource<List<Card>> {
        return try {
            val allCardsList = cardDao.getAllCardsFromLocal()

            Log.d("RRR", "NEWWWS off: ${allCardsList.size ?: -1}")
            NetworkResource.Success(allCardsList)

        } catch (e: HttpException) {
            NetworkResource.Error(UiText.StaticString())
        } catch (e: IOException) {
            NetworkResource.Error(UiText.StaticString())
        }
    }

    suspend fun refreshAllCards(): NetworkResource<List<Card>>{
        val offlineCards = cardDao.getOfflineCards()
        return if (offlineCards.isNotEmpty()) {
            saveCardToServerFromLocal(0, offlineCards)
        } else{
            loadCards()
        }
    }
    private suspend fun loadCards(): NetworkResource<List<Card>> {
        return try {
            val remoteCards = cardService.getAllCards()
            if (remoteCards.isSuccessful) {
                    cardDao.deleteAllCards()
                    val newCards = remoteCards.body()?.map { cardResponse ->
                        cardResponse.toCardDto()
                    }
                    cardDao.insertAllCards(newCards ?: emptyList())

                    NetworkResource.Success(newCards )

            } else {
                NetworkResource.Error(UiText.StaticString())
            }
        } catch (e: HttpException) {
            NetworkResource.Error(UiText.StaticString())
        } catch (e: IOException) {
            NetworkResource.Error(UiText.StaticString())
        }
    }



    private suspend fun saveCardToServerFromLocal(index: Int, cards: List<Card>): NetworkResource<List<Card>> {
        return try {
            val cardRequest = cards[index].cardToCardRequest()
            val newCard = cardService.saveCard(cardRequest)
            if (newCard.isSuccessful) {
                cardDao.update(cards[index])
                val i = index + 1
                if (i < cards.size)
                    saveCardToServerFromLocal(i, cards)
                else {
                    loadCards()
                }
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