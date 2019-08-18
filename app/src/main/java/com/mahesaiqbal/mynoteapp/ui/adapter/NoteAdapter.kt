package com.mahesaiqbal.mynoteapp.ui.adapter

import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mahesaiqbal.mynoteapp.ui.insert.NoteAddUpdateActivity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.mahesaiqbal.mynoteapp.helper.NoteDiffCallback
import android.app.Activity
import android.view.View
import com.mahesaiqbal.mynoteapp.R
import com.mahesaiqbal.mynoteapp.database.Note
import com.mahesaiqbal.mynoteapp.ui.adapter.NoteAdapter.NoteViewHolder
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter(private val activity: Activity) : Adapter<NoteViewHolder>() {

    private val listNotes = arrayListOf<Note>()

    fun setListNotes(lists: MutableList<Note>) {
        val diffCallback = NoteDiffCallback(listNotes, lists)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        listNotes.clear()
        listNotes.addAll(lists)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindItem(listNotes[position])
    }

    override fun getItemCount(): Int = listNotes.size

    inner class NoteViewHolder(itemView: View) : ViewHolder(itemView) {

        fun bindItem(note: Note) {
            itemView.tv_item_title.setText(note.title)
            itemView.tv_item_date.setText(note.date)
            itemView.tv_item_description.setText(note.description)

            itemView.cv_item_note.setOnClickListener {
                val intent = Intent(activity, NoteAddUpdateActivity::class.java)
                intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, adapterPosition)
                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, listNotes[adapterPosition])
                activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE)
            }
        }
    }
}