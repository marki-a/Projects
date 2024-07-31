package hu.bme.aut.android.hf.mynotes

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.hf.mynotes.adapter.LabelAdapter
import hu.bme.aut.android.hf.mynotes.adapter.NoteAdapter
import hu.bme.aut.android.hf.mynotes.adapter.SelectLabelAdapter
import hu.bme.aut.android.hf.mynotes.data.Label
import hu.bme.aut.android.hf.mynotes.data.Note
import hu.bme.aut.android.hf.mynotes.data.NotesDatabase
import hu.bme.aut.android.hf.mynotes.databinding.ActivityMainBinding
import hu.bme.aut.android.hf.mynotes.fragments.NewLabelDialogFragment
import hu.bme.aut.android.hf.mynotes.fragments.NewNoteDialogFragment
import hu.bme.aut.android.hf.mynotes.fragments.NoteEditorDialogFragment
import hu.bme.aut.android.hf.mynotes.fragments.SortingDialogFragment
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(), NoteAdapter.NoteClickListener
    , NoteEditorDialogFragment.NoteEditorDialogListener
    , NewLabelDialogFragment.NewLabelDialogListener
    , NewNoteDialogFragment.NewNoteDialogListener
    , LabelAdapter.LabelClickListener {
    companion object {
        const val GREEN_THEME = 1
        const val BLACK_THEME = 2
    }

    private var currentTheme = GREEN_THEME

    private val STATETHEME = "theme"

    private lateinit var binding: ActivityMainBinding

    private lateinit var database: NotesDatabase

    private lateinit var adapter: NoteAdapter

    private lateinit var tempLabels: String

    private var labelFlag: Boolean = false

    private var sortingFlags = mutableListOf<Label>()

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATETHEME, currentTheme)
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            currentTheme = savedInstanceState.getInt(STATETHEME)
        }
        when (currentTheme) {
            1 -> setTheme(R.style.Theme_MyNotes_Green)
            2 -> setTheme(R.style.Theme_MyNotes_Black)
            else -> setTheme(R.style.Theme_MyNotes_Green)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = NotesDatabase.getDatabase(applicationContext)

        //delete all data from db
        /*
        thread {
            var tempNotes = database.noteDao().getAll()
            for (n in tempNotes) database.noteDao().deleteNote(n)

            var tempLabels = database.labelDao().getAll()
            for (l in tempLabels) database.labelDao().deleteLabel(l)
        }
        */

        initRecyclerView()
        tempLabels = ""
        labelFlag = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val toolbarMenu: Menu = binding.toolbar.menu
        toolbarMenu.clear()
        menuInflater.inflate(R.menu.menu_toolbar, toolbarMenu)
        for (i in 0 until toolbarMenu.size()) {
            val menuItem: MenuItem = toolbarMenu.getItem(i)
            menuItem.setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
            if (menuItem.hasSubMenu()) {
                val subMenu: SubMenu = menuItem.subMenu!!
                for (j in 0 until subMenu.size()) {
                    subMenu.getItem(j)
                        .setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
                }
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.menu_new_note -> {
                NewNoteDialogFragment().show(
                    supportFragmentManager,
                    NoteEditorDialogFragment.TAG
                )
                true
            }
            /*
            R.id.menu_sort_by -> {
                SortingDialogFragment().show(
                    supportFragmentManager,
                    SortingDialogFragment.TAG
                )
                true
            }
            */
            R.id.menu_theme_green -> {
                currentTheme = GREEN_THEME
                item.isChecked = true
                recreate()
                true
            }
            R.id.menu_theme_black -> {
                currentTheme = BLACK_THEME
                item.isChecked = true
                recreate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadItemsInBackground() {
        thread {
            val notes = database.noteDao().getAll()
            runOnUiThread {
                adapter.update(notes)
            }
        }
    }

    private fun initRecyclerView() {
        adapter = NoteAdapter(this)
        binding.recyclerViewNoteList.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewNoteList.adapter = adapter
        loadItemsInBackground()
    }

    override fun onNoteClicked(note: Note) {
        NoteEditorDialogFragment(note).show(
            supportFragmentManager,
            NoteEditorDialogFragment.TAG
        )
    }

    override fun onDeleteNote(note: Note) {
        thread {
            database.noteDao().deleteNote(note)
            Log.d("MainActivity", "Note deleted successfully")
            loadItemsInBackground()
        }
    }

    override fun onNoteCreated(createdNote: Note) {
        thread {
            val insertId = database.noteDao().insert(createdNote)
            createdNote.id = insertId
            runOnUiThread {
                adapter.addNote(createdNote)
            }
            Log.d("MainActivity", "Note creation was successful")
        }
    }

    override fun onNoteEdited(editedNote: Note) {
        thread {
            if (tempLabels != "" || labelFlag) editedNote.labels = tempLabels
            database.noteDao().update(editedNote)
            Log.d("MainActivity", "Note edited successfully")
            loadItemsInBackground()
        }
    }

    override fun onLabelCreated(newLabel: Label) {
        thread {
            if (newLabel.title.isNotEmpty()) {
                val insertId = database.labelDao().insert(newLabel)
                newLabel.id = insertId
                Log.d("MainActivity", "Label created successfully")
            }
            loadItemsInBackground()
        }
    }

    override fun passLabel(string: String, removed: String, note: Note) {
        thread {
            tempLabels = string
            //if (!note.id?.equals(-1)!!) {
                if (removed == "") {
                    labelFlag = false
                    note.labels = string
                    database.noteDao().update(note)
                    Log.d("MainActivity", "Note edited successfullyeeeeeeee")
                    loadItemsInBackground()
                } else if (removed != "") {
                    labelFlag = true
                    var temp = string.split("|").toMutableList()
                    note.labels = temp.joinToString("|")
                    database.noteDao().update(note)
                    Log.d("MainActivity", "Note edited successfullyeeeeeeee")
                    loadItemsInBackground()
                }
            //}
        }
    }

    override fun onLabelChecked(label: Label) {
        sortingFlags.add(label)
    }

    override fun onLabelUnchecked(label: Label) {
        sortingFlags.remove(label)
    }
}