package com.envious.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.envious.domain.model.Machine
import com.envious.domain.model.MachineType

@Entity(tableName = "machine_table")
data class MachineEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String,
    val qrNumber: Long,
    val lastUpdate: String
) {
    fun toMachine(): Machine {
        return Machine(
            id = id,
            name = name,
            type = MachineType.valueOf(type),
            qrNumber = qrNumber,
            lastUpdate = lastUpdate,
        )
    }
}
