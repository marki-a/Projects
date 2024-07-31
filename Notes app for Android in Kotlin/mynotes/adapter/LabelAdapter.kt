package hu.bme.aut.android.hf.mynotes.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.hf.mynotes.data.Label
import hu.bme.aut.android.hf.mynotes.data.Note
import hu.bme.aut.android.hf.mynotes.databinding.LabelListBinding

class LabelAdapter(private val listener: LabelClickListener) :
    RecyclerView.Adapter<LabelAdapter.LabelViewHolder>() {

    private val labels = mutableListOf<Label>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LabelViewHolder(
        LabelListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: LabelViewHolder, position: Int) {
        val label = labels[position]
            holder.binding.textViewLabel.text = label.title
            holder.binding.checkboxSort.setOnClickListener {
                if (holder.binding.checkboxSort.isChecked) {
                    listener.onLabelChecked(label)
                    Log.d("LabelAdapter", "Checkbox checked")
                }
                else if (!holder.binding.checkboxSort.isChecked) {
                    listener.onLabelUnchecked(label)
                    Log.d("LabelAdapter", "Checkbox unchecked")
                }
        }
    }

    override fun getItemCount(): Int = labels.size

    interface LabelClickListener {
        fun onLabelChecked(label: Label)
        fun onLabelUnchecked(label: Label)
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

    inner class LabelViewHolder(val binding: LabelListBinding) : RecyclerView.ViewHolder(binding.root)
}