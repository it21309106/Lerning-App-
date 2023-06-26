package com.example.pkart.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.pkart.R
import com.example.pkart.model.NoteModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MyNoteActivity : AppCompatActivity() {

    private lateinit var noteTitle : EditText
    private lateinit var noteDescription : EditText
    private lateinit var btnSave : Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_note)

        noteTitle = findViewById(R.id.noteTitle)
        noteDescription = findViewById(R.id.noteDescription)
        btnSave = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Notes")

        btnSave.setOnClickListener {
            saveNoteData()
        }

    }

    private fun saveNoteData() {
        //getting values
        val noteTitle = noteTitle.text.toString()
        val noteDescription = noteDescription.text.toString()



        val noteId = dbRef.push().key!!

        val note = NoteModel(noteId,noteTitle,noteDescription)

        dbRef.child(noteId).setValue(note)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}