package com.envious.data.repository

import com.envious.data.local.dao.MachineDao
import com.envious.data.local.model.MachineEntity
import com.envious.domain.model.Machine
import com.envious.domain.repository.MachineRepository
import com.envious.domain.util.Result
import javax.inject.Inject

class MachineRepositoryImpl @Inject constructor(
    private val machineDao: MachineDao
) : MachineRepository {
    override suspend fun getMachineList(): Result<List<Machine>> {
        val items = machineDao.getAllMachine()
        val listData = mutableListOf<Machine>()

        items.forEach {
            listData.add(it!!.toMachine())
        }
        return Result.Success(listData.toList())
    }

    override suspend fun addUpdateMachine(machine: Machine): Result<List<Machine>> {
        machineDao.insert(
            MachineEntity(
                id = machine.id,
                name = machine.name,
                type = machine.type.name,
                qrNumber = machine.qrNumber,
                lastUpdate = machine.lastUpdate
            )
        )

        return getMachineList()
    }

    override suspend fun deleteMachine(id: Int): Result<List<Machine>> {
        machineDao.delete(id)
        return getMachineList()
    }

    override suspend fun getDetailMachine(id: Int): Result<List<Machine>> {
        val items = machineDao.getMachineById(id)
        val listData = mutableListOf<Machine>()

        items.forEach {
            listData.add(it.toMachine())
        }
        return Result.Success(listData.toList())
    }
}
