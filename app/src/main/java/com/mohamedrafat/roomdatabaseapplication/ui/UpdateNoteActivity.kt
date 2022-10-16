package com.mohamedrafat.roomdatabaseapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mohamedrafat.roomdatabaseapplication.R
import com.mohamedrafat.roomdatabaseapplication.databinding.ActivityUpdateNoteBinding
import com.mohamedrafat.roomdatabaseapplication.model.NoteDatabase
import com.mohamedrafat.roomdatabaseapplication.model.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUpdateNoteBinding
    private lateinit var noteDB: NoteDatabase
    private lateinit var noteClicked:NoteEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        noteDB = NoteDatabase.getDatabase(this)

        val noteId = intent.getIntExtra("noteId" , 0)
        GlobalScope.launch(Dispatchers.IO) {
            noteClicked = noteDB.noteDao().getNote(noteId)

            withContext(Dispatchers.Main){
                binding.edtTitle.setText(noteClicked.noteTitle)
                binding.edtDesc.setText(noteClicked.noteDesc)
            }
        }


        binding.btnUpdate.setOnClickListener {
            updateNote(noteId)
        }

        binding.btnDelete.setOnClickListener {
            deleteNote()
        }

    }

    private fun updateNote(noteId:Int){
        val title = binding.edtTitle.text.toString()
        val body = binding.edtDesc.text.toString()

        if(title!=noteClicked.noteTitle || body!=noteClicked.noteDesc){
            GlobalScope.launch(Dispatchers.IO) {
                val noteUpdate = NoteEntity(noteId , title , body)
                noteDB.noteDao().updateNote(noteUpdate)

                withContext(Dispatchers.Main){
                    Toast.makeText(baseContext, "Successfully Updated", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this, "the note is Not Change", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteNote(){
        GlobalScope.launch(Dispatchers.IO) {
            noteDB.noteDao().deleteNote(noteClicked)
            withContext(Dispatchers.Main){
                Toast.makeText(baseContext, "Successfully Deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

}