package hu.bme.aut.android.hf.mynotes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "details") var details: String,
    @ColumnInfo(name = "is_pinned") var isPinned: Boolean,
    @ColumnInfo(name = "labels") var labels: String
)