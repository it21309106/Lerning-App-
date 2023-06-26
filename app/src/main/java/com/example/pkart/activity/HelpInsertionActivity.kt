package com.example.pkart.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.pkart.R
import com.example.pkart.model.HelpModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HelpInsertionActivity : AppCompatActivity() {

    private lateinit var helpSubject : EditText
    private lateinit var helpQuestion : EditText
    private lateinit var helpEmail : EditText
    private lateinit var helpBtnSave : Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_insertion)

        helpSubject = findViewById(R.id.helpSubject)
        helpQuestion = findViewById(R.id.helpQuestion)
        helpEmail = findViewById(R.id.helpEmail)
        helpBtnSave = findViewById(R.id.helpsavebtn)

        dbRef = FirebaseDatabase.getInstance().getReference("HelpDesk")

        helpBtnSave.setOnClickListener {
            saveHelpData()
        }

    }

    private fun saveHelpData() {
        //getting values
        val helpSubject = helpSubject.text.toString()
        val helpQuestion = helpQuestion.text.toString()
        val helpEmail = helpEmail.text.toString()

        val helpId = dbRef.push().key!!

        val help = HelpModel(helpId,helpSubject,helpQuestion,helpEmail)

        dbRef.child(helpId).setValue(help)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }
}