package com.envious.data.usecase

import com.envious.domain.model.Machine
import com.envious.domain.repository.MachineRepository
import com.envious.domain.usecase.BaseCaseWrapper
import com.envious.domain.util.Result
import javax.inject.Inject

class AddUpdateMachineUseCase @Inject constructor(
    private val repository: MachineRepository
) : BaseCaseWrapper<List<Machine>, AddUpdateMachineUseCase.Params>() {

    override suspend fun build(params: Params?): Result<List<Machine>> {
        if (params == null) throw IllegalArgumentException("Params should not be null")

        return repository.addUpdateMachine(params.machine)
    }

    class Params(val machine: Machine)
}
