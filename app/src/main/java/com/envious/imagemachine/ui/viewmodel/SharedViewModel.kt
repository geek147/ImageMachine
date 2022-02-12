package com.envious.imagemachine.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.envious.data.dispatchers.CoroutineDispatchers
import com.envious.data.usecase.*
import com.envious.domain.model.Machine
import com.envious.domain.util.Result
import com.envious.imagemachine.base.BaseViewModel
import com.envious.imagemachine.util.Intent
import com.envious.imagemachine.util.State
import com.envious.imagemachine.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val addUpdateMachineUseCase: AddUpdateMachineUseCase,
    private val deleteMachineUseCase: DeleteMachineUseCase,
    private val getDetailMachineUseCase: GetDetailMachineUseCase,
    private val getMachineListUseCase: GetMachineListUseCase,
    private val ioDispatchers: CoroutineDispatchers,
) : BaseViewModel<Intent, State>(State()) {

    override fun onIntentReceived(intent: Intent) {
        when (intent) {
            is Intent.AddMachine -> saveToMachineList(intent.machine)
            is Intent.DeleteMachine -> removeFromMachineList(intent.id)
            Intent.GetAllMachine -> getAllMachine()
            is Intent.GetDetailMachine -> getDetailMachine(intent.id)
            is Intent.UpdateMachine -> saveToMachineList(intent.machine)
        }
    }

    private fun saveToMachineList(machine: Machine) {
        val params = AddUpdateMachineUseCase.Params(machine)
        viewModelScope.launch {
            when (
                val result = withContext(ioDispatchers.io) {
                    addUpdateMachineUseCase(params)
                }
            ) {
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        setState {
                            copy(
                                listMachine = emptyList(),
                                showLoading = false,
                            )
                        }
                    } else {
                        setState {
                            copy(
                                listMachine = result.data,
                                showLoading = false,
                            )
                        }
                    }
                }
                is Result.Error -> {
                    setState {
                        copy(
                            listMachine = emptyList(),
                            showLoading = false,
                        )
                    }
                }
            }
        }
    }

    private fun removeFromMachineList(id: Int) {
        viewModelScope.launch {
            when (
                val result = withContext(ioDispatchers.io) {
                    deleteMachineUseCase(id)
                }
            ) {
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        setState {
                            copy(
                                listMachine = emptyList(),
                                showLoading = false,
                            )
                        }
                    } else {
                        setState {
                            copy(
                                listMachine = result.data,
                                showLoading = false,
                            )
                        }
                    }
                }
                is Result.Error -> {
                    setState {
                        copy(
                            listMachine = emptyList(),
                            showLoading = false,
                        )
                    }
                }
            }
        }
    }

    private fun getAllMachine() {
        setState {
            copy(
                showLoading = true,
            )
        }

        viewModelScope.launch {

            when (
                val result = withContext(ioDispatchers.io) {
                    getMachineListUseCase()
                }
            ) {
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        setState {
                            copy(
                                listMachine = emptyList(),
                                showLoading = false,
                                viewState = ViewState.EmptyGetAllMachine
                            )
                        }
                    } else {
                        setState {
                            copy(
                                listMachine = result.data,
                                showLoading = false,
                                viewState = ViewState.SuccessGetAllMachine
                            )
                        }
                    }
                }
                is Result.Error -> {
                    setState {
                        copy(
                            listMachine = emptyList(),
                            showLoading = false,
                            viewState = ViewState.ErrorGetAllMachine
                        )
                    }
                }
            }
        }
    }

    private fun getDetailMachine(id: Int) {

        viewModelScope.launch {
            when (
                val result = withContext(ioDispatchers.io) {
                    getDetailMachineUseCase(id)
                }
            ) {
                is Result.Success -> {
                    setState {
                        copy(
                            detailMachine = if (result.data.isNotEmpty()) result.data.first() else null,
                            showLoading = false,
                            viewState = ViewState.SuccessGetDetailMachine
                        )
                    }
                }
                is Result.Error -> {
                    setState {
                        copy(
                            showLoading = false
                        )
                    }
                }
            }
        }
    }
}
