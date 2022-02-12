package com.envious.data.di

import android.content.Context
import androidx.room.Room
import com.envious.data.local.dao.MachineDao
import com.envious.data.local.db.MachineDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideMachineDao(machineDb: MachineDb): MachineDao {
        return machineDb.machineDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MachineDb {
        return Room.databaseBuilder(
            appContext,
            MachineDb::class.java,
            "machine_db"
        ).build()
    }
}
