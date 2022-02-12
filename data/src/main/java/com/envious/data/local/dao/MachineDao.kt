package com.envious.data.local.dao

import androidx.room.* // ktlint-disable no-wildcard-imports
import com.envious.data.local.model.MachineEntity

@Dao
interface MachineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(machine: MachineEntity)

    @Query("SELECT * FROM machine_table")
    fun getAllMachine(): List<MachineEntity?>

    @Query("DELETE FROM machine_table WHERE id = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM machine_table WHERE id = :id")
    fun getMachineById(id: Int): List<MachineEntity>
}
