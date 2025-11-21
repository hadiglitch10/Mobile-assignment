package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.data.Note

class MainActivity : AppCompatActivity() {


    private val myViewModel: NotesViewModel by viewModels()
    

    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val titleInput = findViewById<EditText>(R.id.editNoteTitle)
        val contentInput = findViewById<EditText>(R.id.editNoteContent)
        val categoryInput = findViewById<EditText>(R.id.editNoteCategory)
        
        val btnSave = findViewById<Button>(R.id.buttonSave)
        val categorySpinner = findViewById<Spinner>(R.id.spinnerCategories)
        
        val btnFilter = findViewById<Button>(R.id.buttonFilter)
        val btnShowAll = findViewById<Button>(R.id.buttonShowAll)
        
        val notesList = findViewById<RecyclerView>(R.id.recyclerNotes)


        notesAdapter = NotesAdapter { clickedNote ->

            val i = Intent(this, NoteDetailsActivity::class.java)
            i.putExtra("NOTE_TITLE", clickedNote.title)
            i.putExtra("NOTE_CONTENT", clickedNote.content)
            i.putExtra("NOTE_CATEGORY", clickedNote.category)
            startActivity(i)
        }


        notesList.adapter = notesAdapter
        notesList.layoutManager = LinearLayoutManager(this)

        // updating
        myViewModel.visibleNotes.observe(this) { list ->

            if (list != null) {
                notesAdapter.submitList(list)
            }
        }

        //updating the spinner
        myViewModel.categoriesList.observe(this) { cats ->
            // clean list for the dropdown
            val dropDownList = ArrayList(cats)

            dropDownList.sort()
            
            val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dropDownList)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = spinnerAdapter
        }


        btnSave.setOnClickListener {
            val t = titleInput.text.toString()
            val c = contentInput.text.toString()
            val cat = categoryInput.text.toString()

            // checker
            if (t.isNotEmpty() && c.isNotEmpty() && cat.isNotEmpty()) {
                val newNote = Note(title = t, content = c, category = cat)
                myViewModel.saveNote(newNote)
                

                titleInput.text.clear()
                contentInput.text.clear()
                categoryInput.text.clear()
                
                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Fill in all boxes first", Toast.LENGTH_SHORT).show()
            }
        }


        btnFilter.setOnClickListener {

            val picked = categorySpinner.selectedItem
            
            if (picked != null) {
                val catName = picked.toString()
                myViewModel.filterByCategory(catName)
                Toast.makeText(this, "Showing: $catName", Toast.LENGTH_SHORT).show()
            } else {
                 Toast.makeText(this, "No category to pick!", Toast.LENGTH_SHORT).show()
            }
        }


        btnShowAll.setOnClickListener {
            myViewModel.showAllNotes()
            Toast.makeText(this, "Showing everything", Toast.LENGTH_SHORT).show()
        }
    }
}
