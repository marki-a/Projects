package hu.bme.aut.android.hf.mynotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.hf.mynotes.data.Label
import hu.bme.aut.android.hf.mynotes.data.Note
import hu.bme.aut.android.hf.mynotes.databinding.NoteListBinding

class NoteAdapter(private val listener: NoteClickListener) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val notes = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteViewHolder(
        NoteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.noteTitle.text = note.title.take(30)
        holder.binding.noteDetails.text = note.details.take(200)
        holder.binding.linearLayoutNote.setOnClickListener {
            listener.onNoteClicked(note)
        }
        holder.binding.imageButtonDelete.setOnClickListener {
            listener.onDeleteNote(note)
        }
    }

    override fun getItemCount(): Int = notes.size

    interface NoteClickListener {
        fun onNoteClicked(note: Note)
        fun onDeleteNote(note: Note)
    }

    fun addNote(note: Note) {
        notes.add(note)
        notifyItemInserted(notes.size - 1)
    }

    fun update(noteList: List<Note>) {
        notes.clear()
        notes.addAll(noteList)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(val binding: NoteListBinding) : RecyclerView.ViewHolder(binding.root)
}