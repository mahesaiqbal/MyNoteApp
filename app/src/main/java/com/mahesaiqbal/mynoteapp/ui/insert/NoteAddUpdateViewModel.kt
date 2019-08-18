package com.mahesaiqbal.mynoteapp.ui.insert

import android.app.Application
import com.mahesaiqbal.mynoteapp.repository.NoteRepository
import androidx.lifecycle.ViewModel
import com.mahesaiqbal.mynoteapp.database.Note

class NoteAddUpdateViewModel(application: Application) : ViewModel() {

    private val mNoteRepository = NoteRepository(application)

    fun insert(note: Note) {
        mNoteRepository.insert(note)
    }

    fun update(note: Note) {
        mNoteRepository.update(note)
    }

    fun delete(note: Note) {
        mNoteRepository.delete(note)
    }
}