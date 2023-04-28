package uz.frankie.tasktest.ui

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.frankie.tasktest.R
import uz.frankie.tasktest.databinding.FragmentSplashBinding
import uz.frankie.tasktest.ui.BaseFragment

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

//    val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreate() {
        CoroutineScope(Dispatchers.Main).launch {
//            mainViewModel.getAllCardsList()
            delay(1500)
            navController.navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }

}