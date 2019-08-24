package com.mahesaiqbal.mynoteapp.database

import androidx.paging.DataSource
import androidx.room.*
import androidx.room.OnConflictStrategy

@Dao
interface NoteDao {

    @Query("SELECT * from note ORDER BY id ASC")
    fun getAllNotes(): DataSource.Factory<Int, Note>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    // Menambahkan data dummy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(list: List<Note>)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)
}