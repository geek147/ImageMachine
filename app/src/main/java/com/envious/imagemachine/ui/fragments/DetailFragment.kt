package com.envious.imagemachine.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.Nullable
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.envious.imagemachine.R
import com.envious.imagemachine.base.BaseFragment
import com.envious.imagemachine.databinding.DetailFragmentBinding
import com.envious.imagemachine.util.Intent
import com.envious.imagemachine.util.State
import com.envious.imagemachine.util.ViewState

class DetailFragment : BaseFragment<Intent,
    State>() {

    companion object {
        val TAG = this::class.simpleName
        const val EXTRA_ID = "EXTRA_ID"

        @JvmStatic
        fun create(
            machineId: Int
        ): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = Bundle().apply {
                putInt(EXTRA_ID, machineId)
            }

            return fragment
        }
    }

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private var machineId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DetailFragmentBinding.inflate(layoutInflater)
        machineId = arguments?.getInt(EXTRA_ID) ?: 0
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) {
            invalidate(it)
        }
        viewModel.onIntentReceived(Intent.GetDetailMachine(machineId))
        setUpButtonOpenGallery()
    }

    private fun setUpButtonOpenGallery() {
        with(binding) {
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle the up button here
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) ||
            super.onOptionsItemSelected(item)
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override val layoutResourceId: Int
        get() = R.layout.detail_fragment

    override fun invalidate(state: State) {

        when (state.viewState) {
            ViewState.SuccessGetDetailMachine -> {
                with(binding) {
                    textName.text = state.detailMachine?.name
                    textType.text = state.detailMachine?.type?.name
                    textQr.text = state.detailMachine?.qrNumber.toString()
                    textLastUpdate.text = state.detailMachine?.lastUpdate
                }
            }
        }
    }
}
