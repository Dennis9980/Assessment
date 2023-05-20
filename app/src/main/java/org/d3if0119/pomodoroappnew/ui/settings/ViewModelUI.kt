package org.d3if0119.pomodoroappnew.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelUI(application: Application) : AndroidViewModel(application) {
    // DataStore
    private val uiDataStore = UIModePreference(application)

    // get UI mode
    val getUIMode = uiDataStore.uiMode

    // save UI mode
    fun saveToDataStore(isNightMode: Boolean) {
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                uiDataStore.saveToDataStore(isNightMode)
            }
        }
    }
}