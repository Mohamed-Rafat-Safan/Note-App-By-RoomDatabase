package com.mohamedrafat.roomdatabaseapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes_table")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int?,
    @ColumnInfo(name = "note_title")
    val noteTitle:String,
    @ColumnInfo(name = "note_desc")
    val noteDesc: String
)