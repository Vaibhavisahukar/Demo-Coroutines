package com.example.demoproject.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.demoproject.data.model.ResultsItem
import com.example.demoproject.data.model.User

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: ArrayList<ResultsItem>)

    @Query("SELECT * from ResultsItem")
    fun getData(): LiveData<List<ResultsItem>>

    @Delete
    suspend fun delete(resultsItem: ResultsItem)
}
