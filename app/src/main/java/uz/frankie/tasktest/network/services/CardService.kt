package uz.frankie.tasktest.network.services

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import uz.frankie.tasktest.network.models.CardRequest
import uz.frankie.tasktest.network.models.CardResponse

interface CardService {

    @GET("cards")
    suspend fun getAllCards(): Response<List<CardResponse>>

    @POST("cards")
    suspend fun saveCard(@Body cardRequest: CardRequest): Response<CardResponse>

}