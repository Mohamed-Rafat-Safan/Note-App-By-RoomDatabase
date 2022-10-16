package com.mohamedrafat.roomdatabaseapplication.model

import androidx.room.*

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Query("DELETE FROM Notes_table")
    suspend fun deleteAllNotes()


    @Query("SELECT * FROM Notes_table ORDER BY noteId DESC")
    suspend fun getAllNotes() : List<NoteEntity>

    @Query("SELECT * FROM Notes_table WHERE noteId LIKE :id")
    suspend fun getNote(id : Int) : NoteEntity
}