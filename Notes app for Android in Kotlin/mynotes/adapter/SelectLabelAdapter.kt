package hu.bme.aut.android.hf.mynotes.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.hf.mynotes.data.Label
import hu.bme.aut.android.hf.mynotes.data.Note
import hu.bme.aut.android.hf.mynotes.databinding.SelectLabelListBinding

class SelectLabelAdapter(private val listener: LabelClickListener, note: Note) :
    RecyclerView.Adapter<SelectLabelAdapter.LabelViewHolder>() {

    private val labels = mutableListOf<Label>()

    private val note = note

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LabelViewHolder(
        SelectLabelListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: LabelViewHolder, position: Int) {
        val label = labels[position]
        holder.binding.textViewLabel.text = label.title
        if (label.title in note.labels) holder.binding.checkboxLabel.isChecked = true
        holder.binding.checkboxLabel.setOnClickListener {
            if (holder.binding.checkboxLabel.isChecked) {
                listener.onLabelChecked(label)
                Log.d("SelectLabelAdapter", "Checkbox checked")
            }
            else if (!holder.binding.checkboxLabel.isChecked) {
                listener.onLabelUnchecked(label)
                Log.d("SelectLabelAdapter", "Checkbox unchecked")
            }
        }
    }

    override fun getItemCount(): Int = labels.size

    interface LabelClickListener {
        fun onLabelChecked(label: Label)
        fun onLabelUnchecked(label: Label)
        //fun onLabelChanged(string: String, removed: String)
    }

    fun addLabel(label: Label) {
        labels.add(label)
        notifyItemInserted(labels.size - 1)
    }

    fun update(labelList: List<Label>) {
        labels.clear()
        labels.addAll(labelList)
        notifyDataSetChanged()
    }

    inner class LabelViewHolder(val binding: SelectLabelListBinding) : RecyclerView.ViewHolder(binding.root)
}