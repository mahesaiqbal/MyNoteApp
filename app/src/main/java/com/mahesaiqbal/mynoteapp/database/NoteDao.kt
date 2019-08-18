package com.mahesaiqbal.mynoteapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * from note ORDER BY id ASC")
    fun getAllNotes(): LiveData<MutableList<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)
}