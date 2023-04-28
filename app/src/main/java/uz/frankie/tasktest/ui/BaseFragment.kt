package uz.frankie.tasktest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import uz.frankie.tasktest.utils.NetworkConnectionListener
import uz.frankie.tasktest.utils.extension.collectLatestLA
import uz.frankie.tasktest.viewmodels.MainViewModel

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment(){

    private var _binding: VB? = null
    val binding get() = _binding!!
    var isOnline = true

    lateinit var navController: NavController
    private val networkListener by lazy { NetworkConnectionListener() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        onViewCreate()

    }

    abstract fun onViewCreate()

}