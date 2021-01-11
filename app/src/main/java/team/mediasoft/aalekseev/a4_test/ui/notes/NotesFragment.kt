package team.mediasoft.aalekseev.a4_test.ui.notes

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import team.mediasoft.aalekseev.a4_test.R
import team.mediasoft.aalekseev.a4_test.databinding.FragmentNotesBinding
import team.mediasoft.aalekseev.a4_test.ui.core.Fragment


@AndroidEntryPoint
class NotesFragment : Fragment(R.layout.fragment_notes) {

    private val viewModel: NotesViewModel by viewModels()

    private val viewAdapter = NotesViewAdapter(
        { viewModel.onNoteClick(it) },
        { viewModel.onNoteLongClick(it) }
    )
    private var isDeletionMode: Boolean = false
    private lateinit var binding: FragmentNotesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotesBinding.bind(requireView())
            .setup()
        observeVM()
    }

    override fun observeVM(): Disposable {
        return CompositeDisposable(
            viewModel.noteViewsObservable.observe {
                viewAdapter.submitList(it)
            },
            viewModel.isDeletingModeObservable.observe { isDeleting ->
                binding.confirmDeleteButton.isInvisible = !isDeleting
                binding.cancelDeleteButton.isInvisible = !isDeleting
                binding.toolbarTitle.isInvisible = isDeleting
                isDeletionMode = isDeleting
            }
        )
    }

    private fun FragmentNotesBinding.setup(): FragmentNotesBinding {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbarTitle.text = "List of notes"

        cancelDeleteButton.setOnClickListener { viewModel.removeAllNotesForDelete() }
        confirmDeleteButton.setOnClickListener { viewModel.deleteSelected() }

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_NotesFragment_to_AddNoteFragment)
        }

        recyclerView.setup()
        return this
    }

    private fun onClickAtItemInRV(id: NoteView) {
        if (isDeletionMode) {
            viewModel.addOrRemoveNoteForDelete(id)
        } else {
            findNavController().navigate(
                R.id.action_NotesFragment_to_editNoteFragment,
                Bundle().also { bundle ->
                    bundle.putInt("noteId", id.note.id)
                })
        }
    }

    private fun onLongClickAtItemInRV(id: NoteView): Boolean =
        if (isDeletionMode) {
            false
        } else {
            viewModel.addOrRemoveNoteForDelete(id)
            true
        }


    private fun RecyclerView.setup() {
        layoutManager = LinearLayoutManager(activity)

        adapter = this@NotesFragment.viewAdapter
        viewAdapter.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY

        addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            ).also {
                val drawable = GradientDrawable(
                    GradientDrawable.Orientation.BOTTOM_TOP,
                    intArrayOf(solidColor, solidColor)
                )
                drawable.setSize(0, 20)
                it.setDrawable(drawable)
            }
        )
    }

}