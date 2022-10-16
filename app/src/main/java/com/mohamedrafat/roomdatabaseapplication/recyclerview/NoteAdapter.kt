package com.mohamedrafat.roomdatabaseapplication.recyclerview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mohamedrafat.roomdatabaseapplication.databinding.ItemNoteBinding
import com.mohamedrafat.roomdatabaseapplication.model.NoteEntity
import com.mohamedrafat.roomdatabaseapplication.ui.UpdateNoteActivity
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter(val listNotes:List<NoteEntity>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>(){
    private lateinit var binding: ItemNoteBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemNoteBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) {
        val noteEntity = listNotes[position]

        holder.bind(noteEntity)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, UpdateNoteActivity::class.java)
            intent.putExtra("noteId", noteEntity.noteId)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return listNotes.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.tvTitle
        val body = itemView.tvDesc

        fun bind(noteEntity: NoteEntity){
            title.text = noteEntity.noteTitle
            body.text = noteEntity.noteDesc
        }
    }

}