package com.mohamedrafat.roomdatabaseapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mohamedrafat.roomdatabaseapplication.databinding.ActivityAddNoteBinding
import com.mohamedrafat.roomdatabaseapplication.model.NoteDatabase
import com.mohamedrafat.roomdatabaseapplication.model.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Suppress("NAME_SHADOWING")
class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var noteDB:NoteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteDB = NoteDatabase.getDatabase(this)

        binding.btnSave.setOnClickListener {
            addNote()
        }
    }

    private fun addNote(){
        val title = binding.edtTitle.text.toString()
        val description = binding.edtDesc.text.toString()

        if(title.isNotEmpty() && description.isNotEmpty()){
            val noteEntity = NoteEntity(null , title, description)
            GlobalScope.launch(Dispatchers.IO) {
                noteDB.noteDao().insertNote(noteEntity)
            }
            binding.edtTitle.text.clear()
            binding.edtDesc.text.clear()
            Toast.makeText(this, "Successfully inserted", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this , MainActivity::class.java))
        }else{
            Toast.makeText(this, "Please write title & body", Toast.LENGTH_SHORT).show()
        }
    }

}


