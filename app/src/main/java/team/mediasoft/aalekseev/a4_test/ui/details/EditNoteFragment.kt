package team.mediasoft.aalekseev.a4_test.ui.details

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import team.mediasoft.aalekseev.a4_test.R
import team.mediasoft.aalekseev.a4_test.databinding.FragmentEditNoteBinding
import team.mediasoft.aalekseev.a4_test.ui.core.Fragment

@AndroidEntryPoint
class EditNoteFragment : Fragment(R.layout.fragment_edit_note) {

    private val viewModel: EditNoteViewModel by viewModels()
    private lateinit var binding : FragmentEditNoteBinding
    private val noteId by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments().getInt("noteId")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditNoteBinding.bind(requireView())
        observeVM()
    }

    override fun observeVM(): Disposable {
        return CompositeDisposable(
            viewModel.getNoteById(noteId).observe { note ->
                binding.noteName.text = SpannableStringBuilder(note.name)
                binding.noteDescription.text = SpannableStringBuilder(note.description)
            }
        )
    }
}