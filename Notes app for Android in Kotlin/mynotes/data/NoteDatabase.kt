package hu.bme.aut.android.hf.mynotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class, Label::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun labelDao(): LabelDao

    companion object {
        fun getDatabase(applicationContext: Context): NotesDatabase {
            return Room.databaseBuilder(
                applicationContext,
                NotesDatabase::class.java,
                "notes"
            ).build();
        }
    }
}