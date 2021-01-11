package team.mediasoft.aalekseev.a4_test.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import team.mediasoft.aalekseev.a4_test.R
import team.mediasoft.aalekseev.a4_test.databinding.NotesHolderBinding

class NotesViewAdapter(
    private val onClick: (NoteView) -> Unit,
    private val onLongClick: (NoteView) -> Unit
) : ListAdapter<NoteView, NotesViewAdapter.NotesViewHolder>(NotesDiffCallback()) {

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: NotesHolderBinding by lazy(LazyThreadSafetyMode.NONE) {
            NotesHolderBinding.bind(itemView)
        }

        lateinit var noteView: NoteView

        init {
            binding.root.setOnClickListener { onClick(noteView) }
            binding.root.setOnLongClickListener {
                onLongClick(noteView)
                true
            }
        }


        fun bind() {
            renderDeletingCheckboxVisibility()
            renderSelected()
            renderName()
            renderDescription()
        }

        fun renderDeletingCheckboxVisibility() {
            binding.noteDeleteCheckBox.isInvisible = !noteView.isDeletionMode
        }

        fun renderSelected() {
            binding.noteDeleteCheckBox.isChecked = noteView.isSelected
        }

        fun renderName() {
            binding.noteName.text = noteView.note.name
        }

        fun renderDescription() {
            binding.noteDesc.text = noteView.note.description
        }
    }

    class NotesDiffCallback : DiffUtil.ItemCallback<NoteView>() {

        override fun areItemsTheSame(
            oldItem: NoteView,
            newItem: NoteView
        ): Boolean = oldItem.note.id == newItem.note.id

        override fun areContentsTheSame(
            oldItem: NoteView,
            newItem: NoteView
        ): Boolean = oldItem == newItem

        override fun getChangePayload(
            oldItem: NoteView,
            newItem: NoteView
        ) = mutableListOf<NotesViewHolder.() -> Unit>().apply {
            if (oldItem.isSelected != newItem.isSelected) add { renderSelected() }
            if (oldItem.isDeletionMode != newItem.isDeletionMode) add { renderDeletingCheckboxVisibility() }
            if (oldItem.note.name != newItem.note.name) add { renderName() }
            if (oldItem.note.description != newItem.note.description) add { renderDescription() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.notes_holder,
                parent,
                false
            )
    )

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.noteView = getItem(position)
        holder.bind()
    }

    override fun onBindViewHolder(
        holder: NotesViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.noteView = getItem(position)

        if (payloads.isEmpty()) {
            holder.bind()
        }

        @Suppress("UNCHECKED_CAST")
        for (changes in payloads as List<List<NotesViewHolder.() -> Unit>>) {
            for (change in changes) {
                change(holder)
            }
        }
    }
}