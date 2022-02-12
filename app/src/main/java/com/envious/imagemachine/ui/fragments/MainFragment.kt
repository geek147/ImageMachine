package com.envious.imagemachine.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.envious.imagemachine.R
import com.envious.imagemachine.base.BaseFragment
import com.envious.imagemachine.databinding.MainFragmentBinding
import com.envious.imagemachine.ui.adapter.MachineAdapter
import com.envious.imagemachine.util.Intent
import com.envious.imagemachine.util.State
import com.envious.imagemachine.util.ViewState

class MainFragment :
    BaseFragment<Intent,
        State>() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MachineAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = MainFragmentBinding.inflate(layoutInflater)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {
            invalidate(it)
        }
        setupRecyclerView()
        viewModel.onIntentReceived(Intent.GetAllMachine)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerview.setHasFixedSize(true)
            val linearLayoutManager = LinearLayoutManager(requireContext())
            recyclerview.layoutManager = linearLayoutManager
            recyclerview.itemAnimator = null
            adapter = MachineAdapter(this@MainFragment)
            adapter.setHasStableIds(true)
            recyclerview.adapter = adapter
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.main_fragment

    override fun invalidate(state: State) {
        with(binding) {
            pgProgressList.visibility = if (state.showLoading) View.VISIBLE else View.GONE
        }

        when (state.viewState) {
            ViewState.EmptyGetAllMachine -> {
                with(binding) {
                    errorView.visibility = View.VISIBLE
                    errorView.run {
                        setUpErrorView(
                            title = resources.getString(R.string.empty_state_title),
                            message = resources.getString(R.string.empty_state_message)
                        )
                        binding.buttonRetry.setOnClickListener {
                            viewModel.onIntentReceived(Intent.GetAllMachine)
                        }
                    }
                    adapter.setData(emptyList())
                    recyclerview.visibility = View.GONE
                }
            }
            ViewState.ErrorGetAllMachine -> {
                with(binding) {
                    errorView.visibility = View.VISIBLE
                    errorView.run {
                        setUpErrorView()
                        binding.buttonRetry.setOnClickListener {
                            viewModel.onIntentReceived(Intent.GetAllMachine)
                        }
                    }
                    adapter.setData(emptyList())
                    recyclerview.visibility = View.GONE
                }
            }
            ViewState.Idle -> {}
            ViewState.SuccessGetAllMachine -> {
                with(binding) {
                    recyclerview.visibility = View.VISIBLE
                    adapter.setData(state.listMachine)
                    errorView.visibility = View.GONE
                }
            }
        }
    }
}
