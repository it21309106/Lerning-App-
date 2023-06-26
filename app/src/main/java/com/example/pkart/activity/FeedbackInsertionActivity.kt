package com.example.pkart.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.pkart.R
import com.example.pkart.model.FeedbackModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FeedbackInsertionActivity : AppCompatActivity() {

    private lateinit var feedbackName : EditText
    private lateinit var feedbackGrade : EditText
    private lateinit var feedbackFeedback : EditText
    private lateinit var feedbackBtnSave : Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_insertion)

        feedbackName = findViewById(R.id.feedbackName)
        feedbackGrade = findViewById(R.id.feedbackGrade)
        feedbackFeedback = findViewById(R.id.feedbackFeedback)
        feedbackBtnSave = findViewById(R.id.feedbacksavebtn)

        dbRef = FirebaseDatabase.getInstance().getReference("Feedback")

        feedbackBtnSave.setOnClickListener {
            saveHelpData()
        }

    }

    private fun saveHelpData() {
        //getting values
        val feedbackName = feedbackName.text.toString()
        val feedbackGrade = feedbackGrade.text.toString()
        val feedbackFeedback = feedbackFeedback.text.toString()

        val feedbackId = dbRef.push().key!!

        val feedback = FeedbackModel(feedbackId,feedbackName,feedbackGrade,feedbackFeedback)

        dbRef.child(feedbackId).setValue(feedback)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                    err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }
}