package hu.bme.aut.android.hf.mynotes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): List<Note>

    @Insert
    fun insert(notes: Note): Long

    @Update
    fun update(note: Note)

    @Delete
    fun deleteNote(note: Note)
}