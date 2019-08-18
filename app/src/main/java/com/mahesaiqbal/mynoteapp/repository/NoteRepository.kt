package com.mahesaiqbal.mynoteapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.mahesaiqbal.mynoteapp.database.Note
import com.mahesaiqbal.mynoteapp.database.NoteRoomDatabase
import com.mahesaiqbal.mynoteapp.database.NoteDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NoteRepository(application: Application) {

    private val mNotesDao: NoteDao
    private val executorService: ExecutorService
    private val db: NoteRoomDatabase

    init {
        executorService = Executors.newSingleThreadExecutor()

        db = NoteRoomDatabase.getDatabase(application)!!
        mNotesDao = db.noteDao()
    }

    fun getAllNotes(): LiveData<MutableList<Note>> = mNotesDao.getAllNotes()

    fun insert(note: Note) {
        executorService.execute(Runnable { mNotesDao.insert(note) })
    }

    fun delete(note: Note) {
        executorService.execute(Runnable { mNotesDao.delete(note) })
    }

    fun update(note: Note) {
        executorService.execute(Runnable { mNotesDao.update(note) })
    }
}