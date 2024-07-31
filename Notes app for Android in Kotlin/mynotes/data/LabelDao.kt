package hu.bme.aut.android.hf.mynotes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LabelDao {
    @Query("SELECT * FROM label")
    fun getAll(): List<Label>

    @Insert
    fun insert(labels: Label): Long

    @Update
    fun update(label: Label)

    @Delete
    fun deleteLabel(label: Label)
}