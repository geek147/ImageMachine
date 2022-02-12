package com.envious.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.envious.data.local.dao.MachineDao
import com.envious.data.local.model.MachineEntity

@Database(entities = [MachineEntity::class], version = 1, exportSchema = false)
abstract class MachineDb : RoomDatabase() {
    abstract fun machineDao(): MachineDao
}
