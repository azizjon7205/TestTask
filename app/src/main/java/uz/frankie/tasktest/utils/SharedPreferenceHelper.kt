package uz.frankie.tasktest.utils

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.frankie.tasktest.utils.Constants.INTERNET_CONNECTION
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferenceHelper @Inject constructor(@ApplicationContext val context: Context) {

    val preferences by lazy {
        context.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
    }

    fun setInternetConnection(token: Boolean) {
        preferences.edit {
            putBoolean(INTERNET_CONNECTION, token)
        }
    }

    fun getInternetConnection() = preferences.getBoolean(INTERNET_CONNECTION, false)

}