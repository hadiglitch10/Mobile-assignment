package com.example.assignment.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// interacting with the database here
@Dao
interface NoteDao {


    @Insert
    suspend fun addNote(note: Note)


    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>


    @Query("SELECT * FROM notes WHERE category = :catName ORDER BY id DESC")
    fun getNotesByCategory(catName: String): LiveData<List<Note>>
    

    @Query("SELECT DISTINCT category FROM notes")
    fun getCategories(): LiveData<List<String>>
}
