package com.mahesaiqbal.mynoteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.os.AsyncTask.execute
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import java.util.concurrent.Executors


@Database(entities = arrayOf(Note::class), version = 1)
abstract class NoteRoomDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        @Volatile
        private var INSTANCE: NoteRoomDatabase? = null

        fun getDatabase(context: Context): NoteRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(NoteRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            NoteRoomDatabase::class.java, "note_database"
                        ).build()

//                        add()
                    }
                }
            }
            return INSTANCE!!
        }

        private fun add() {
            Executors.newSingleThreadExecutor().execute {
                val list = arrayListOf<Note>()
                for (i in 0.rangeTo(29)) {
                    list.add(Note(i, "Belajar Modul $i", ""))
                }
                INSTANCE?.noteDao()?.insertAll(list)
            }
        }
    }
}