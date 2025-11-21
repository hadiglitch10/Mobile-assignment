package com.example.assignment

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NoteDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)

        // getting the data
        val t = intent.getStringExtra("NOTE_TITLE")
        val c = intent.getStringExtra("NOTE_CONTENT")
        val cat = intent.getStringExtra("NOTE_CATEGORY")


        val titleView = findViewById<TextView>(R.id.detailTitle)
        val contentView = findViewById<TextView>(R.id.detailContent)
        val catView = findViewById<TextView>(R.id.detailCategory)


        titleView.text = t
        contentView.text = c
        catView.text = "Category: $cat"
    }
}
