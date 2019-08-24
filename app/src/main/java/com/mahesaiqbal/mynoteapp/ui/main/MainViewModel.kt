package com.mahesaiqbal.mynoteapp.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import com.mahesaiqbal.mynoteapp.repository.NoteRepository
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mahesaiqbal.mynoteapp.database.Note


class MainViewModel(application: Application) : ViewModel() {

    private val mNoteRepository = NoteRepository(application)

    fun getAllNotes(): LiveData<PagedList<Note>> = LivePagedListBuilder(mNoteRepository.getAllNotes(), 20).build()
}