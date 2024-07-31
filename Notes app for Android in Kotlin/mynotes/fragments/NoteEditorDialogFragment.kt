package hu.bme.aut.android.hf.mynotes.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.hf.mynotes.adapter.LabelAdapter
import hu.bme.aut.android.hf.mynotes.data.Label
import hu.bme.aut.android.hf.mynotes.data.Note
import hu.bme.aut.android.hf.mynotes.data.NotesDatabase
import hu.bme.aut.android.hf.mynotes.databinding.DialogNoteEditorBinding
import kotlin.concurrent.thread

class NoteEditorDialogFragment (note: Note) : DialogFragment()
    , NewLabelDialogFragment.NewLabelDialogListener {
    interface NoteEditorDialogListener {
        fun onNoteEdited(editedNote: Note)
    }

    private var note = note

    private lateinit var listener: NoteEditorDialogListener

    private lateinit var binding: DialogNoteEditorBinding

    private lateinit var database: NotesDatabase

    //private lateinit var adapter: LabelAdapter

    private var labels = mutableListOf<Label>()

    private var labelNames = mutableListOf<String>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NoteEditorDialogListener
            ?: throw RuntimeException("Activity must implement the NoteEditorDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        database = NotesDatabase.getDatabase(requireActivity().application)

        binding = DialogNoteEditorBinding.inflate(LayoutInflater.from(context))

        binding.editTextTitle.setText(note.title)

        binding.editTextDetails.setText(note.details)

        binding.spinnerAddLabel.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            labelNames
        )

        //binding.checkboxPinned.isChecked = note.isPinned

        binding.buttonNewLabel.setOnClickListener { dialogInterface ->
            NewLabelDialogFragment(note).show(
                parentFragmentManager,
                NewLabelDialogFragment.TAG
            )
        }

        loadItemsInBackground()

        return AlertDialog.Builder(requireContext())
            .setTitle("Edit Note")
            .setView(binding.root)
            .setPositiveButton("Done") { dialogInterface, i ->
                listener.onNoteEdited(getNote())
            }
            .setNegativeButton("Cancel", null)
            .create()
    }

    private fun getNote(): Note {
        note.title = binding.editTextTitle.text.toString()
        note.details = binding.editTextDetails.text.toString()
        note.isPinned = false //binding.checkboxPinned.isChecked
        note.labels = labelNames.joinToString("|")
        return note
    }

    private fun loadItemsInBackground() {
        thread {
            labels = database.labelDao().getAll().toMutableList()
            for (label in labels) {
                if (label.title in note.labels) labelNames.add(label.title)
            }
            val dataLabels = database.labelDao().getAll()
            //adapter.update(dataLabels)
        }
    }

    /*
    override fun onLabelClicked(label: Label) {
        thread {
            var labels = mutableListOf<String>()
            labels = note.labels.split("|").toMutableList()
            labels.removeAt(labels.indexOf(label.title))
            note.labels = labels.joinToString("|")
            database.noteDao().update(note)
            Log.d("NoteEditorDialogFragment", "Label removed")
            loadItemsInBackground()
        }
    }
     */

    override fun onLabelCreated(newLabel: Label) {
        //if (newLabel.title.isNotEmpty()) adapter.addLabel(newLabel)
        note.labels = note.labels + "|" + newLabel.title
        listener.onNoteEdited(note)
        loadItemsInBackground()
    }

    override fun passLabel(string: String, removed: String, note: Note) {
        thread {
            if (!note.id?.equals(-1)!!) {
                if (removed == "") {
                    note.labels = string
                    database.noteDao().update(note)
                    Log.d("MainActivity", "Note edited successfully")
                    loadItemsInBackground()
                } else {
                    var temp = string.split("|").toMutableList()
                    temp.removeAt(temp.indexOf(removed))
                    note.labels = temp.joinToString("|")
                    database.noteDao().update(note)
                    Log.d("MainActivity", "Note edited successfully")
                    loadItemsInBackground()
                }
            }
        }
    }

    companion object {
        const val TAG = "NoteEditorDialogFragment"
    }
}