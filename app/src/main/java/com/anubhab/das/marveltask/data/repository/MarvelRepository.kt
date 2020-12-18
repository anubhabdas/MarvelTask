package com.anubhab.das.marveltask.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.anubhab.das.marveltask.data.room.Marvel
import com.anubhab.das.marveltask.data.room.MarvelDAO

class MarvelRepository(private val marvelDao: MarvelDAO) {

    val allMarvelCharacters: LiveData<List<Marvel>> = marvelDao.getAllMarvelCharacters()

    @WorkerThread
    suspend fun insert(marvel: Marvel) {
        marvelDao.addMarvelCharacter(marvel)
    }

    @WorkerThread
    suspend fun delete(marvel: Marvel){
        marvelDao.deleteMarvelCharacter(marvel)
    }

    @WorkerThread
    suspend fun deleteAll(){
        marvelDao.deleteAll()
    }
}