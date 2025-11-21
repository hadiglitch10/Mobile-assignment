package com.example.assignment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assignment.data.AppDatabase
import com.example.assignment.data.Note
import kotlinx.coroutines.launch

class NotesViewModel(app: Application) : AndroidViewModel(app) {


    private val db = AppDatabase.getDatabase(app).noteDao()


    private val _visibleNotes = MutableLiveData<List<Note>>()
    val visibleNotes: LiveData<List<Note>> = _visibleNotes

    // dropdown
    val categoriesList: LiveData<List<String>> = db.getCategories()

    // cache
    private var allNotesCache = listOf<Note>()

    init {
        loadAllNotes()
    }

    fun saveNote(n: Note) {
        viewModelScope.launch {
            db.addNote(n)
        }
    }


    fun filterByCategory(cat: String) {
        val filteredList = allNotesCache.filter { it.category == cat }
        _visibleNotes.value = filteredList
    }


    fun showAllNotes() {
        _visibleNotes.value = allNotesCache
    }

    private fun loadAllNotes() {

        val notesLiveData = db.getAllNotes()
        
        notesLiveData.observeForever { newList ->
            allNotesCache = newList
            _visibleNotes.value = allNotesCache
        }
    }
}
