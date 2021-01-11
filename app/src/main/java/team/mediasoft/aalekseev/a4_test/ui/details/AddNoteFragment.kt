package team.mediasoft.aalekseev.a4_test.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import team.mediasoft.aalekseev.a4_test.R
import team.mediasoft.aalekseev.a4_test.databinding.FragmentAddNoteBinding
import team.mediasoft.aalekseev.a4_test.ui.core.Fragment

@AndroidEntryPoint
class AddNoteFragment : Fragment(R.layout.fragment_add_note) {
    private val viewModel: AddNoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FragmentAddNoteBinding.bind(requireView()).setup()
    }

    private fun FragmentAddNoteBinding.setup(): FragmentAddNoteBinding {
        done.setOnClickListener {
            viewModel.addNewNote(
                noteName.text.toString(),
                noteDescription.text.toString()
            )
            requireActivity().onBackPressed()
        }
        undone.setOnClickListener {
            requireActivity().onBackPressed()
        }
        return this
    }
}