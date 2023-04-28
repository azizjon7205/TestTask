package uz.frankie.tasktest.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.frankie.tasktest.models.Card
import uz.frankie.tasktest.utils.Constants

@Database(entities = [Card::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCardDao(): CardDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {

            if (instance == null)
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    Constants.CARDS_DATABASE
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

            return instance!!
        }
    }
}
