package uz.frankie.tasktest.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.frankie.tasktest.MainActivity
import uz.frankie.tasktest.R
import uz.frankie.tasktest.adapter.CardsAdapter
import uz.frankie.tasktest.databinding.FragmentHomeBinding
import uz.frankie.tasktest.utils.extension.collectLA
import uz.frankie.tasktest.viewmodels.MainViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val cardsAdapter by lazy { CardsAdapter() }

    override fun onViewCreate() {
        binding.ivSaveCard.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_createCardFragment)
        }
        binding.rvCards.adapter = cardsAdapter

        collectUiState()
    }


    private fun collectUiState() {
        (requireActivity() as MainActivity).mainViewModel.uiState.collectLA(viewLifecycleOwner) { uiState ->
//            binding.flLoading.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
            if (uiState.cardList.isNotEmpty()) {
                // set data Adapter or UI
                cardsAdapter.submitList(uiState.cardList)
            }

            if (uiState.isLoading) {
                val loaderDialog = true  // loaderDialog show
            } else {
                val loaderDialog = false  // loaderDialog dismiss
            }

            Log.d("RRR", "Error: ${uiState.errorMessage}")
        }
    }
}