package com.mobileforce.hometeach.data.sources.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobileforce.hometeach.data.sources.local.entities.WalletEntity

@Dao
interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWalletInfo(wallet: WalletEntity)

    @Query("SELECT * FROM wallet_info")
    fun observeWalletInfo(): LiveData<WalletEntity>

}