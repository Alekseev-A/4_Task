package team.mediasoft.aalekseev.a4_test.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import team.mediasoft.aalekseev.a4_test.data.NotesDao
import team.mediasoft.aalekseev.a4_test.data.NotesDatabase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(
        app: Application,
        callback: NotesDatabase.Callback
    ): NotesDatabase =
        Room.databaseBuilder(app, NotesDatabase::class.java, "notes_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

    @Provides
    fun provideNotesDao(db: NotesDatabase): NotesDao = db.getNotesDao()


}