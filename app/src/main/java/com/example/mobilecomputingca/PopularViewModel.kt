package com.example.mobilecomputingca

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilecomputingca.api.RetrofitInstance
import com.example.mobilecomputingca.model.Film
import kotlinx.coroutines.launch

class PopularViewModel : ViewModel() {

    private val _films: MutableLiveData<List<Film>> = MutableLiveData()

    //Using mutable live data so it can be changed at run time
    val films: MutableLiveData<List<Film>>

        get() = _films


    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    //When app is initialised
    init {
        getPopular()
    }

    //Function to get all currently popular films
    fun getPopular() {
        viewModelScope.launch {
            //Loading variable
            _isLoading.value = true
            //Use retrofit to make an API call to get all films
            val fetchedFilms = RetrofitInstance.api.getPopular()
            Log.i(TAG, "Got posts: $fetchedFilms")
            //Put the response from the API in film.value
            _films.value = fetchedFilms.results
            //The app is no longer loading/waiting for api response
            _isLoading.value = false

        }
    }
}