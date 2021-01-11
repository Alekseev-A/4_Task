package team.mediasoft.aalekseev.a4_test.ui.notes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import team.mediasoft.aalekseev.a4_test.data.NotesDao

class NotesViewModel @ViewModelInject constructor(
    private val notesDao: NotesDao
) : ViewModel() {

    private val selectedNotesBehaviorSubject = BehaviorSubject.createDefault(emptySet<Int>())

    val noteViewsObservable: Observable<List<NoteView>> = Observable
        .combineLatest(
            notesDao.getNotes(),
            selectedNotesBehaviorSubject,
        ) { notes, forDelete ->
            notes.map { note ->
                NoteView(
                    note,
                    forDelete.contains(note.id),
                    forDelete.isNotEmpty()
                )
            }
        }

    val isDeletingModeObservable: Observable<Boolean> = selectedNotesBehaviorSubject
        .map { it.isNotEmpty() }
        .distinctUntilChanged()

    fun onNoteClick(noteView: NoteView) {

    }

    fun onNoteLongClick(noteView: NoteView) {

    }

    fun addOrRemoveNoteForDelete(noteView: NoteView) = selectedNotesBehaviorSubject.onNext(
        if (noteView.isSelected) {
            selectedNotesBehaviorSubject.value - noteView.note.id
        } else {
            selectedNotesBehaviorSubject.value + noteView.note.id
        }
    )

    fun removeAllNotesForDelete() = selectedNotesBehaviorSubject.onNext(emptySet())

    fun deleteSelected(): Disposable = Completable
        .fromAction {
            for (selected in selectedNotesBehaviorSubject.value) {
                notesDao.deleteNote(selected)
            }
        }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            selectedNotesBehaviorSubject.onNext(emptySet())
        }
}