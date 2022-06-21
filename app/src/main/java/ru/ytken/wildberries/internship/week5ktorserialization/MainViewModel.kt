package ru.ytken.wildberries.internship.week5ktorserialization

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ytken.wildberries.internship.week5ktorserialization.data.Repository
import ru.ytken.wildberries.internship.week5ktorserialization.entities.*

class MainViewModel(app: Application): AndroidViewModel(app) {
    private val repository: Repository = (app as App).api().repository

    val currentCat = MutableLiveData<CatEntity>()
    val nextCatToShow = MutableLiveData<CatEntity>()
    val listOfFavouriteCats = MutableLiveData<List<GetFavouritesEntity>>()

    init {
        viewModelScope.launch {
            currentCat.postValue(getRandomCat())
            nextCatToShow.postValue(getRandomCat())
        }
    }

    private suspend fun getRandomCat(): CatEntity {
        val listEntities = withContext(viewModelScope.coroutineContext) {
            repository.getNewCat()
        }
        return listEntities[0]
    }
    private suspend fun showNextCat() {
        currentCat.postValue(nextCatToShow.value)
        nextCatToShow.postValue(getRandomCat())
    }

    fun likeCurrentCat() {
        viewModelScope.launch {
            currentCat.value?.let { repository.likeCat(it) }
            showNextCat()
        }
    }

    fun dislikeCurrentCat() {
        viewModelScope.launch {
            currentCat.value?.let { repository.dislikeCat(it) }
            showNextCat()
        }
    }

    fun saveCurrentCatToFavourites() {
        viewModelScope.launch {
            currentCat.value?.let { repository.addFavourite(it) }
        }
    }

    fun getListOfFavourites() {
        viewModelScope.launch {
            listOfFavouriteCats.postValue(repository.readAllFavourites())
        }
    }
}