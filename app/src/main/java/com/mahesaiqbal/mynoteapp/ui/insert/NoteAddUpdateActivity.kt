package com.mahesaiqbal.mynoteapp.ui.insert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mahesaiqbal.mynoteapp.R
import com.mahesaiqbal.mynoteapp.database.Note
import androidx.lifecycle.ViewModelProviders
import com.mahesaiqbal.mynoteapp.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_note_add_update.*
import com.mahesaiqbal.mynoteapp.helper.DateHelper
import android.content.Intent
import android.content.DialogInterface
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import java.lang.Exception

class NoteAddUpdateActivity : AppCompatActivity() {

    var isEdit: Boolean = false

    var note: Note? = null
    lateinit var viewModel: NoteAddUpdateViewModel

    var position = 0

    companion object {
        val EXTRA_NOTE = "extra_note"
        val EXTRA_POSITION = "extra_position"

        val REQUEST_ADD = 100
        val RESULT_ADD = 101
        val REQUEST_UPDATE = 200
        val RESULT_UPDATE = 201
        val RESULT_DELETE = 301

        val ALERT_DIALOG_CLOSE = 10
        val ALERT_DIALOG_DELETE = 20
    }

    private fun obtainViewModel(activity: AppCompatActivity): NoteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProviders.of(activity, factory).get(NoteAddUpdateViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add_update)

        viewModel = obtainViewModel(this)

        try {
            note = intent.getParcelableExtra(EXTRA_NOTE)
            if (note != null) {
                position = intent.getIntExtra(EXTRA_POSITION, 0)
                isEdit = true
            } else {
                note = Note()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)

            if (note != null) {
                edt_title.setText(note?.title)
                edt_description.setText(note?.description)
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        if (supportActionBar != null) {
            supportActionBar!!.setTitle(actionBarTitle)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        btn_submit.setText(btnTitle)
        btn_submit.setOnClickListener {
            val title = edt_title.getText().toString().trim()
            val description = edt_description.getText().toString().trim()

            if (title.isEmpty()) {
                edt_title.setError(getString(R.string.empty))
            } else if (description.isEmpty()) {
                edt_description.setError(getString(R.string.empty))
            } else {
                note?.title = title
                note?.description = description

                val intent = Intent()
                intent.putExtra(EXTRA_NOTE, note)
                intent.putExtra(EXTRA_POSITION, position)

                if (isEdit) {
                    viewModel.update(note!!)
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    note?.date = DateHelper.getCurrentDate()
                    viewModel.insert(note!!)
                    setResult(RESULT_ADD, intent)
                    finish()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes), DialogInterface.OnClickListener { dialog, id ->
                if (isDialogClose) {
                    finish()
                } else {
                    viewModel.delete(note!!)

                    val intent = Intent()
                    intent.putExtra(EXTRA_POSITION, position)
                    setResult(RESULT_DELETE, intent)
                    finish()

                }
            })
            .setNegativeButton(getString(R.string.no),
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }
}
