package org.d3if0119.pomodoroappnew.ui.recomendation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0119.pomodoroappnew.model.Book
import org.d3if0119.pomodoroappnew.network.ApiStatus
import org.d3if0119.pomodoroappnew.network.BookApi

class RecomendationViewModel : ViewModel(){
    private val data = MutableLiveData<List<Book>>()
    private val status = MutableLiveData<ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData(){
        viewModelScope.launch (Dispatchers.IO){
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue(BookApi.service.getBook())
                status.postValue((ApiStatus.SUCCESS))
            }catch (e: Exception){
                Log.d("RecomendationViewModel", "Gagal: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }
    fun getData(): LiveData<List<Book>> = data
    fun getStatus(): LiveData<ApiStatus> = status

}