package com.example.testassignment.presentation.core

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.testassignment.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class BaseMVVMFragment<out T : BaseViewModel, VB : ViewBinding>(
    inflate: Inflate<VB>
) : BaseFragment<VB>(inflate) {

    protected abstract val viewModel: T

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.noInternetConnectionLiveData.observe(viewLifecycleOwner) {
            showNoInternetDialog()
        }
    }

    private fun showNoInternetDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.error_internet_connection)
            .setPositiveButton(R.string.action_try_again) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDetach() {
        super.onDetach()
    }
}