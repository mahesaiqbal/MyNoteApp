package com.mahesaiqbal.mynoteapp.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.paging.PagedListAdapter
import com.mahesaiqbal.mynoteapp.R
import com.mahesaiqbal.mynoteapp.database.Note
import com.mahesaiqbal.mynoteapp.ui.adapter.NotePagedListAdapter.NoteViewHolder
import com.mahesaiqbal.mynoteapp.ui.insert.NoteAddUpdateActivity
import kotlinx.android.synthetic.main.item_note.view.*

class NotePagedListAdapter(private val activity: Activity) :
    PagedListAdapter<Note, NoteViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldNote: Note, newNote: Note): Boolean {
                return oldNote.title.equals(newNote.title)
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldNote: Note, newNote: Note): Boolean {
                return oldNote.equals(newNote)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note: Note = getItem(position)!!
        holder.bindItem(note)
    }

    inner class NoteViewHolder(itemView: View) : ViewHolder(itemView) {

        fun bindItem(note: Note) {
            itemView.tv_item_title.setText(note.title)
            itemView.tv_item_date.setText(note.date)
            itemView.tv_item_description.setText(note.description)

            itemView.cv_item_note.setOnClickListener {
                val intent = Intent(activity, NoteAddUpdateActivity::class.java)
                intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, adapterPosition)
                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
                activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE)
            }
        }
    }
}