package com.envious.domain.repository

import com.envious.domain.model.Machine
import com.envious.domain.util.Result

interface MachineRepository {

    suspend fun getMachineList(): Result<List<Machine>>

    suspend fun addUpdateMachine(
        machine: Machine
    ): Result<List<Machine>>

    suspend fun deleteMachine(
        id: Int
    ): Result<List<Machine>>

    suspend fun getDetailMachine(
        id: Int
    ): Result<List<Machine>>
}
