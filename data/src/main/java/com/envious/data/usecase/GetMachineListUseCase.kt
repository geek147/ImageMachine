package com.envious.data.usecase

import com.envious.domain.model.Machine
import com.envious.domain.repository.MachineRepository
import com.envious.domain.usecase.BaseCaseWrapper
import com.envious.domain.util.Result
import javax.inject.Inject

class GetMachineListUseCase @Inject constructor(
    private val repository: MachineRepository
) : BaseCaseWrapper<List<Machine>, Unit>() {

    override suspend fun build(params: Unit?): Result<List<Machine>> {
        if (params == null) throw IllegalArgumentException("Params should not be null")

        return repository.getMachineList()
    } }
