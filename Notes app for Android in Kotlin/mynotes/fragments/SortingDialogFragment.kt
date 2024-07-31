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
import hu.bme.aut.android.hf.mynotes.databinding.DialogSortBinding
import hu.bme.aut.android.hf.mynotes.databinding.SelectLabelListBinding
import kotlin.concurrent.thread

class SortingDialogFragment : DialogFragment()
    , LabelAdapter.LabelClickListener {
    interface NewLabelDialogListener {
        fun onSortingSet(labels: MutableList<Label>)
    }

    private lateinit var listener: NewLabelDialogListener

    private lateinit var binding: DialogSortBinding

    private lateinit var adapter: LabelAdapter

    private lateinit var database: NotesDatabase

    private var labels = mutableListOf<Label>()

    private var sortingLabels = mutableListOf<Label>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewLabelDialogListener
            ?: throw RuntimeException("Activity must implement the NewLabelDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogSortBinding.inflate(LayoutInflater.from(context))
        adapter = LabelAdapter(this)
        database = NotesDatabase.getDatabase(requireActivity().application)

        initRecyclerView()

        return android.app.AlertDialog.Builder(requireContext())
            .setTitle("New Label")
            .setView(binding.root)
            .setPositiveButton("Done") { dialogInterface, i ->
                listener.onSortingSet(sortingLabels)
            }
            .setNegativeButton("Cancel", null)
            .create()
    }

    private fun loadItemsInBackground() {
        thread {
            val dataLabels = database.labelDao().getAll()
            adapter.update(dataLabels)
        }
    }

    private fun initRecyclerView() {
        adapter = LabelAdapter(this)
        binding.recyclerViewSort.layoutManager = LinearLayoutManager(activity
            , LinearLayoutManager.HORIZONTAL
            , false)
        binding.recyclerViewSort.adapter = adapter
        loadItemsInBackground()
    }

    override fun onLabelChecked(label: Label) {
        sortingLabels.add(label)
    }

    override fun onLabelUnchecked(label: Label) {
        sortingLabels.remove(label)
    }

    companion object {
        const val TAG = "SortingDialogFragment"
    }
}