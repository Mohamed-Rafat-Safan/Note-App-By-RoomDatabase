package com.mohamedrafat.roomdatabaseapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohamedrafat.roomdatabaseapplication.databinding.ActivityMainBinding
import com.mohamedrafat.roomdatabaseapplication.model.NoteDatabase
import com.mohamedrafat.roomdatabaseapplication.model.NoteEntity
import com.mohamedrafat.roomdatabaseapplication.recyclerview.NoteAdapter
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var noteDB: NoteDatabase
    private lateinit var allNote: List<NoteEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteDB = NoteDatabase.getDatabase(this)

        binding.btnAddNote.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }


        binding.btnDeleteAll.setOnClickListener {
            deleteAllNotes()
        }

    }

    override fun onResume() {
        super.onResume()
        // this method get all note and return in recyclerview
        getNotes()
    }


    private fun getNotes() {
        GlobalScope.launch(Dispatchers.IO) {
            allNote = noteDB.noteDao().getAllNotes()
            if (allNote.size > 0) {
                binding.rvNoteList.visibility = View.VISIBLE
                binding.tvEmptyText.visibility = View.GONE

                withContext(Dispatchers.Main) {
                    val adapter = NoteAdapter(allNote)
                    adapter.notifyDataSetChanged()
                    binding.rvNoteList.layoutManager = LinearLayoutManager(baseContext)
                    binding.rvNoteList.adapter = adapter
                    binding.rvNoteList.setHasFixedSize(true)
                }
            }else{
                withContext(Dispatchers.Main) {
                    binding.rvNoteList.visibility = View.GONE
                    binding.tvEmptyText.visibility = View.VISIBLE

                    binding.tvEmptyText.text = "database is empty"
                }
            }
        }
    }


    private fun deleteAllNotes(){
        GlobalScope.launch(Dispatchers.IO) {
            if(allNote.isNotEmpty()){
                noteDB.noteDao().deleteAllNotes()
                withContext(Dispatchers.Main) {
                    getNotes()
                    Toast.makeText(baseContext, "Successfully Delete All Notes", Toast.LENGTH_SHORT).show()
                }
            }else{
                withContext(Dispatchers.Main) {
                    Toast.makeText(baseContext, "Not Found Data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


//  hello mohamed