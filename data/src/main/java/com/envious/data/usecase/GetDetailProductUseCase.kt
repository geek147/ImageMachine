package com.envious.data.usecase

import com.envious.domain.model.Machine
import com.envious.domain.repository.MachineRepository
import com.envious.domain.usecase.BaseCaseWrapper
import com.envious.domain.util.Result
import javax.inject.Inject

class GetDetailMachineUseCase @Inject constructor(
    private val repository: MachineRepository
) : BaseCaseWrapper<List<Machine>, Int>() {

    override suspend fun build(params: Int?): Result<List<Machine>> {
        if (params == null) throw IllegalArgumentException("Params should not be null")

        return repository.getDetailMachine(params)
    }
}
