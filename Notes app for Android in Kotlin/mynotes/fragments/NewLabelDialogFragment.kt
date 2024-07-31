package hu.bme.aut.android.hf.mynotes.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.hf.mynotes.adapter.LabelAdapter
import hu.bme.aut.android.hf.mynotes.adapter.SelectLabelAdapter
import hu.bme.aut.android.hf.mynotes.data.Label
import hu.bme.aut.android.hf.mynotes.data.Note
import hu.bme.aut.android.hf.mynotes.data.NotesDatabase
import hu.bme.aut.android.hf.mynotes.databinding.DialogNewLabelBinding
import hu.bme.aut.android.hf.mynotes.databinding.SelectLabelListBinding
import kotlin.concurrent.thread

class NewLabelDialogFragment (note: Note) : DialogFragment()
    , SelectLabelAdapter.LabelClickListener {
    interface NewLabelDialogListener {
        fun onLabelCreated(newLabel: Label)
        fun passLabel(string: String, removed: String, note: Note)
    }

    private lateinit var listener: NewLabelDialogListener

    private lateinit var binding: DialogNewLabelBinding

    private lateinit var adapter: SelectLabelAdapter

    private lateinit var database: NotesDatabase

    private var labels = mutableListOf<Label>()

    private var labelNames = mutableListOf<String>()

    private var note = note

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewLabelDialogListener
            ?: throw RuntimeException("Activity must implement the NewLabelDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewLabelBinding.inflate(LayoutInflater.from(context))
        adapter = SelectLabelAdapter(this, note)
        database = NotesDatabase.getDatabase(requireActivity().application)

        initRecyclerView()

        return android.app.AlertDialog.Builder(requireContext())
            .setTitle("New Label")
            .setView(binding.root)
            .setPositiveButton("Done") { dialogInterface, i ->
                listener.onLabelCreated(getLabel())
            }
            .setNegativeButton("Cancel", null)
            .create()
    }

    private fun loadItemsInBackground() {
        thread {
            labels = database.labelDao().getAll().toMutableList()
            for (label in labels) {
                labelNames.add(label.title)
            }
            val dataLabels = database.labelDao().getAll()
            adapter.update(dataLabels)
        }
    }

    private fun initRecyclerView() {
        adapter = SelectLabelAdapter(this, note)
        binding.recyclerViewLabels.layoutManager = LinearLayoutManager(activity
            , LinearLayoutManager.HORIZONTAL
            , false)
        binding.recyclerViewLabels.adapter = adapter
        loadItemsInBackground()
    }

    private fun getLabel() = Label(
        title = binding.editTextTitle.text.toString()
    )

    override fun onLabelChecked(label: Label) {
        var labels = mutableListOf<String>()
        labels = note.labels.split("|").toMutableList()
        labels.add(label.title)
        note.labels = labels.joinToString("|")
        listener.passLabel(note.labels, "", note)
    }

    override fun onLabelUnchecked(label: Label) {
        var labels = mutableListOf<String>()
        labels = note.labels.split("|").toMutableList()
        if (label.title in labels) labels.removeAt(labels.indexOf(label.title))
        note.labels = labels.joinToString("|")
        listener.passLabel(note.labels, label.title, note)
    }

    companion object {
        const val TAG = "NewLabelDialogFragment"
    }
}