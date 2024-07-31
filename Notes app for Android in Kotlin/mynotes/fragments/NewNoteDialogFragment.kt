package hu.bme.aut.android.hf.mynotes.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.hf.mynotes.adapter.LabelAdapter
import hu.bme.aut.android.hf.mynotes.data.Label
import hu.bme.aut.android.hf.mynotes.data.Note
import hu.bme.aut.android.hf.mynotes.data.NotesDatabase
import hu.bme.aut.android.hf.mynotes.databinding.DialogNoteEditorBinding
import kotlin.concurrent.thread

class NewNoteDialogFragment : DialogFragment()
    , NewLabelDialogFragment.NewLabelDialogListener {
    interface NewNoteDialogListener {
        fun onNoteCreated(createdNote: Note)
    }

    private lateinit var listener: NewNoteDialogListener

    private lateinit var binding: DialogNoteEditorBinding

    private lateinit var database: NotesDatabase

    //private lateinit var adapter: LabelAdapter

    private var labels = mutableListOf<Label>()

    private var labelNames = mutableListOf<String>()

    private lateinit var note: Note

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewNoteDialogListener
            ?: throw RuntimeException("Activity must implement the NewNoteDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        note = Note(
            -1,
            "",
            "",
            false,
            ""
        )

        database = NotesDatabase.getDatabase(requireActivity().application)

        binding = DialogNoteEditorBinding.inflate(LayoutInflater.from(context))

        binding.spinnerAddLabel.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            labelNames
        )

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
                listener.onNoteCreated(getNote())
            }
            .setNegativeButton("Cancel", null)
            .create()
    }

    private fun getNote() = Note(
        title = binding.editTextTitle.text.toString(),
        details = binding.editTextDetails.text.toString(),
        isPinned = false, //binding.checkboxPinned.isChecked,
        labels = note.labels
    )

    private fun loadItemsInBackground() {
        thread {
            labels = database.labelDao().getAll().toMutableList()
            for (label in labels) {
                labelNames.add(label.title)
            }
            val dataLabels = database.labelDao().getAll()
            //adapter.update(dataLabels)
        }
    }

    /*
    override fun onLabelClicked(label: Label) {
        thread {
            var note = getNote()
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
        //adapter.addLabel(newLabel)
        note.labels += "|" + newLabel.title
        loadItemsInBackground()
    }

    override fun passLabel(string: String, removed: String, note: Note) {
        /*
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
         */
    }

    companion object {
        const val TAG = "NewNoteDialogFragment"
    }
}