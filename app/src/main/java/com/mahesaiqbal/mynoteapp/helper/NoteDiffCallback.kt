package com.mahesaiqbal.mynoteapp.helper

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.mahesaiqbal.mynoteapp.database.Note

class NoteDiffCallback(val mOldNoteList: MutableList<Note>, val mNewNoteList: MutableList<Note>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldNoteList[oldItemPosition].id === mNewNoteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldNoteList[oldItemPosition]
        val newEmployee = mNewNoteList[newItemPosition]

        return oldEmployee.title.equals(newEmployee.title)
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    override fun getOldListSize(): Int = mOldNoteList.size

    override fun getNewListSize(): Int = mNewNoteList.size
}