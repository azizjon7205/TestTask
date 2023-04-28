package uz.frankie.tasktest.local

import androidx.room.*
import uz.frankie.tasktest.models.Card

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: Card)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCards(cards: List<Card>)

    @Query("SELECT * FROM cards")
    suspend fun getAllCardsFromLocal(): List<Card>

    @Query("SELECT * FROM cards where isUploaded=0")
    fun getOfflineCards(): List<Card>

    @Update
    fun update(card: Card)

    @Query("DELETE FROM cards WHERE id=:id")
    suspend fun deleteCard(id: String)

    @Query("DELETE FROM cards")
    suspend fun deleteAllCards()
}