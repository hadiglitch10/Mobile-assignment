package com.example.assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.data.Note


class NotesAdapter(

    private val onNoteClicked: (Note) -> Unit
) : RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {


    private var notesList: List<Note> = emptyList()

    // update the data
    fun submitList(newList: List<Note>) {
        notesList = newList
        notifyDataSetChanged()
    }

    // view for a single row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return MyViewHolder(v)
    }

    // Bind data to the view
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentNote = notesList[position]
        
        holder.titleText.text = currentNote.title
        holder.categoryText.text = "(${currentNote.category})"


        holder.itemView.setOnClickListener {
            onNoteClicked(currentNote)
        }
    }


    override fun getItemCount(): Int {
        return notesList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.textNoteTitle)
        val categoryText: TextView = itemView.findViewById(R.id.textNoteCategory)
    }
}
