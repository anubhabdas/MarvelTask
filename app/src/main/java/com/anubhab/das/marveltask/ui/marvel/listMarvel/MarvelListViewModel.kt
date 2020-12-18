package com.anubhab.das.marveltask.ui.marvel.listMarvel

import androidx.lifecycle.*
import com.anubhab.das.marveltask.data.repository.MarvelRepository
import com.anubhab.das.marveltask.data.room.Marvel
import kotlinx.coroutines.launch

class MarvelListViewModel(private val repo: MarvelRepository): ViewModel() {

    val allMarvelCharacter: LiveData<List<Marvel>> = repo.allMarvelCharacters

    fun insertMarvelChar(marvel: Marvel) = viewModelScope.launch {
        repo.insert(marvel)
    }

    fun deleteMarvelChar(marvel: Marvel) = viewModelScope.launch {
        repo.delete(marvel)
    }

    fun deleteAllMarvelChar() = viewModelScope.launch {
        repo.deleteAll()
    }
}

class MarvelListViewModelFactory(private val repo: MarvelRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarvelListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MarvelListViewModel(
                repo
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}