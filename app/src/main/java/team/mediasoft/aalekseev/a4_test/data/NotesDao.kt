package team.mediasoft.aalekseev.a4_test.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject


@Dao
interface NotesDao {
    @Query("SELECT * FROM note_table ")
    fun getNotes() : Observable<List<Note>>

    @Query("SELECT * FROM note_table where id is :id ")
    fun getNoteByID(id: Int) : Observable<Note>

    @Insert(onConflict = REPLACE)
    fun insertNote (note: Note)

    @Query("DELETE FROM note_table where id is :id" )
    fun deleteNote (id: Int)

    @Query("DELETE FROM note_table")
    fun deleteAll ()
}