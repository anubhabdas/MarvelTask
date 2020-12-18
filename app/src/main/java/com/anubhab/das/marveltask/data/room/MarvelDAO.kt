package com.anubhab.das.marveltask.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.anubhab.das.marveltask.data.room.Marvel

@Dao
interface MarvelDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMarvelCharacter(marvel: Marvel)

    @Query("Select * from marvel_table")
    fun getAllMarvelCharacters() : LiveData<List<Marvel>>

    @Delete
    suspend fun deleteMarvelCharacter(marvel: Marvel)

    @Query("Delete from marvel_table")
    suspend fun deleteAll()
}