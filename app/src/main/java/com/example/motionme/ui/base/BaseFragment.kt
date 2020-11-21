package com.example.motionme.ui.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.motionme.extension.observe
import com.example.motionme.widget.LoadingDialog
import org.jetbrains.anko.support.v4.toast

abstract class BaseFragment<ViewModelType : BaseViewModel> : Fragment() {

    @LayoutRes
    abstract fun getLayoutResId(): Int

    protected abstract val viewModel: ViewModelType
    private var loadingDialog: LoadingDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            observe(_showLoading) {
                if (loadingDialog == null) {
                    loadingDialog = LoadingDialog(requireContext())
                }

                loadingDialog?.show()
            }

            observe(_hideLoading) {
                loadingDialog?.dismiss()
            }

            observe(_showError) {
                AlertDialog.Builder(requireContext())
                    .setMessage(it)
                    .setPositiveButton("Ok") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }

            observe(_showToast) {
                toast(it)
            }
        }
    }

    override fun onDestroy() {
        loadingDialog = null
        viewModel.apply {
            _showLoading.removeObservers(this@BaseFragment)
            _hideLoading.removeObservers(this@BaseFragment)
            _showError.removeObservers(this@BaseFragment)
            _showToast.removeObservers(this@BaseFragment)
        }

        super.onDestroy()
    }

}