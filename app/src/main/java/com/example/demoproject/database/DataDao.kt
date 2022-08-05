package com.example.demoproject.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.demoproject.data.model.ResultsItem

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: List<ResultsItem>)

    @Query("SELECT * from ResultsItem")
    fun getData(): LiveData<List<ResultsItem>>

    @Query("DELETE FROM ResultsItem")
    suspend fun deleteUsers()

    @Delete
    suspend fun delete(resultsItem: ResultsItem)
}
