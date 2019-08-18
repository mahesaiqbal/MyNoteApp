package com.mahesaiqbal.mynoteapp.viewmodel

import android.app.Application
import com.mahesaiqbal.mynoteapp.ui.insert.NoteAddUpdateViewModel
import com.mahesaiqbal.mynoteapp.ui.main.MainViewModel
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory

class ViewModelFactory (val mApplication: Application) : NewInstanceFactory() {

    companion object {

        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory? {

            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = ViewModelFactory(application)
                    }
                }
            }
            return INSTANCE
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(NoteAddUpdateViewModel::class.java)) {
            return NoteAddUpdateViewModel(mApplication) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}