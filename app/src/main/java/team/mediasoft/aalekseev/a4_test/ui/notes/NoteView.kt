package team.mediasoft.aalekseev.a4_test.ui.notes

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import team.mediasoft.aalekseev.a4_test.data.Note

@Parcelize
data class NoteView(
    val note: Note,
    var isSelected: Boolean,
    var isDeletionMode : Boolean
): Parcelable