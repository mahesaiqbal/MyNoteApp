package com.mahesaiqbal.mynoteapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahesaiqbal.mynoteapp.R
import com.mahesaiqbal.mynoteapp.ui.adapter.NoteAdapter
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.ViewModelProviders
import com.mahesaiqbal.mynoteapp.viewmodel.ViewModelFactory
import androidx.lifecycle.Observer
import com.mahesaiqbal.mynoteapp.database.Note
import com.mahesaiqbal.mynoteapp.ui.insert.NoteAddUpdateActivity
import android.content.Intent
import androidx.paging.PagedList
import com.mahesaiqbal.mynoteapp.ui.adapter.NotePagedListAdapter
import com.mahesaiqbal.mynoteapp.ui.insert.NoteAddUpdateActivity.Companion.REQUEST_UPDATE

class MainActivity : AppCompatActivity() {

    lateinit var noteAdapter: NotePagedListAdapter
    lateinit var viewModel: MainViewModel

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProviders.of(activity, factory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = obtainViewModel(this)
        viewModel.getAllNotes().observe(this, noteObserver)

        noteAdapter = NotePagedListAdapter(this)

        rv_notes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        fab_add.setOnClickListener { v ->
            if (v.getId() == R.id.fab_add) {
                val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
                startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_ADD)
            }
        }
    }

    private val noteObserver = object : Observer<PagedList<Note>> {
        override fun onChanged(noteList: PagedList<Note>?) {
            if (noteList != null) {
                noteAdapter.submitList(noteList)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == NoteAddUpdateActivity.REQUEST_ADD) {
                if (resultCode == NoteAddUpdateActivity.RESULT_ADD) {
                    showSnackbarMessage(getString(R.string.added))
                }
            } else if (requestCode == REQUEST_UPDATE) {
                if (resultCode == NoteAddUpdateActivity.RESULT_UPDATE) {
                    showSnackbarMessage(getString(R.string.changed))
                } else if (resultCode == NoteAddUpdateActivity.RESULT_DELETE) {
                    showSnackbarMessage(getString(R.string.deleted))
                }
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_notes, message, Snackbar.LENGTH_SHORT).show()
    }
}
