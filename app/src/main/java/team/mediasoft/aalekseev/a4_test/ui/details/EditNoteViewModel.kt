package team.mediasoft.aalekseev.a4_test.ui.details


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import team.mediasoft.aalekseev.a4_test.data.Note
import team.mediasoft.aalekseev.a4_test.data.NotesDao

class EditNoteViewModel@ViewModelInject constructor(
    private val notesDao: NotesDao
): ViewModel() {

    fun getNoteById(id: Int) : Observable<Note> = notesDao.getNoteByID(id)
}