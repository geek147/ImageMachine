package com.envious.imagemachine.util

import com.envious.domain.model.Machine

sealed class Intent {
    object GetAllMachine : Intent()
    data class DeleteMachine(val id: Int) : Intent()
    data class GetDetailMachine(val id: Int) : Intent()
    data class AddMachine(val machine: Machine) : Intent()
    data class UpdateMachine(val machine: Machine) : Intent()
}

data class State(
    val showLoading: Boolean = false,
    val listMachine: List<Machine> = listOf(),
    val viewState: ViewState = ViewState.Idle,
    val detailMachine: Machine? = null
)

sealed class ViewState {
    object Idle : ViewState()
    object SuccessGetAllMachine : ViewState()
    object EmptyGetAllMachine : ViewState()
    object ErrorGetAllMachine : ViewState()
    object SuccessDeleteMachine : ViewState()
    object ErrorDeleteMachine : ViewState()
    object SuccessGetDetailMachine : ViewState()
    object EmptyGetDetailMachine : ViewState()
    object ErrorGetDetailMachine : ViewState()
    object SuccessAddMachine : ViewState()
    object ErrorAddMachine : ViewState()
    object SuccessUpdateMachine : ViewState()
    object ErrorUpdateMachine : ViewState()
}
