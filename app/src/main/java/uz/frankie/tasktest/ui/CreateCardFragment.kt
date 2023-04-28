package uz.frankie.tasktest.ui

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.text.set
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.frankie.tasktest.MainActivity
import uz.frankie.tasktest.databinding.FragmentCreateCardBinding
import uz.frankie.tasktest.models.Card
import uz.frankie.tasktest.utils.SharedPreferenceHelper
import uz.frankie.tasktest.utils.extension.collectLA
import uz.frankie.tasktest.viewmodels.CreateCardViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CreateCardFragment : BaseFragment<FragmentCreateCardBinding>(FragmentCreateCardBinding::inflate) {

    private val createCardViewModel: CreateCardViewModel by viewModels()

    @Inject
    lateinit var preferences: SharedPreferenceHelper

    override fun onViewCreate() {

        initViews()
        collectUiState()
    }

    private fun initViews() {
        binding.apply {
            ivBack.setOnClickListener {
                navController.navigateUp()
            }
            bSave.setOnClickListener {
                if (etCardNumber.text.isNotEmpty() && etCardName.text.isNotEmpty() && etCardExpdate.text.isNotEmpty()){
                    val card = Card(cardNumber = etCardNumber.text.toString(),
                        cardName = etCardName.text.toString(),
                        expireDate = etCardExpdate.text.toString()
                    )
                    if (preferences.getInternetConnection()){
                        createCardViewModel.saveToServer(card)
                    } else{
                        card.isUploaded = false
                        createCardViewModel.saveToLocal(card)
                    }
                }
            }

            etCardNumber.addTextChangedListener{
                tvCardNumber.text = etCardNumber.text.toString()
                if (etCardNumber.text.toString().isNotEmpty() && etCardNumber.text.toString().length % 4 == 0)
                    etCardNumber.text.insert(1, " ")
            }

            etCardName.addTextChangedListener {
                tvCardName.text = etCardName.text.toString()
            }

            etCardExpdate.addTextChangedListener {
                tvCardBalance.text = etCardExpdate.text.toString()
            }

        }
    }

    private fun collectUiState() {
        createCardViewModel.uiState.collectLA(viewLifecycleOwner) { uiState ->
//            binding.flLoading.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
            if (uiState.card != null) {
                // set data Adapter or UI
                navController.navigateUp()

                (requireActivity() as MainActivity).mainViewModel.getAllCardsList()
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