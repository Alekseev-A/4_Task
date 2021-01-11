package team.mediasoft.aalekseev.a4_test.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import team.mediasoft.aalekseev.a4_test.data.Note
import team.mediasoft.aalekseev.a4_test.data.NotesDao

class AddNoteViewModel@ViewModelInject constructor(
    private val notesDao: NotesDao
): ViewModel() {

    fun addNewNote(name: String, description: String) {
        Completable.fromAction {
                notesDao.insertNote(Note(name = name, description = description))
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}