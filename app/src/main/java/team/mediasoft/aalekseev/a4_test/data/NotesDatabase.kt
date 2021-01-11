package team.mediasoft.aalekseev.a4_test.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.OnConflictStrategy
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao

    class Callback @Inject constructor(
        private val database: Provider<NotesDao>
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            Thread{
                val dao = database.get()
                dao.insertNote(Note(name = "Heelooo", description = "check"))
                dao.insertNote(Note(name = "adwwa", description = "Yakiii"))
            }.start()
        }
    }
}