package uz.frankie.tasktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.frankie.tasktest.utils.NetworkConnectionListener
import uz.frankie.tasktest.utils.SharedPreferenceHelper
import uz.frankie.tasktest.utils.extension.collectLatestLA
import uz.frankie.tasktest.viewmodels.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val networkListener by lazy { NetworkConnectionListener() }
    @Inject
    lateinit var preferences: SharedPreferenceHelper
    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews(){

        networkListener.checkNetworkAvailability(this)
            .collectLatestLA(this) { status ->
                preferences.setInternetConnection(status)
//                Toast.makeText(this, status.toString(), Toast.LENGTH_SHORT).show()
                when (status) {
                    true -> {
                        mainViewModel.refreshAllCardsList()
                    }
                    false -> {
                        mainViewModel.getAllCardsList()
                    }
                }
            }

    }
}