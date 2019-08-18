package com.mahesaiqbal.mynoteapp.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import com.mahesaiqbal.mynoteapp.repository.NoteRepository
import androidx.lifecycle.ViewModel
import com.mahesaiqbal.mynoteapp.database.Note


class MainViewModel(application: Application) : ViewModel() {

    private val mNoteRepository = NoteRepository(application)

    fun getAllNotes(): LiveData<MutableList<Note>> = mNoteRepository.getAllNotes()
}