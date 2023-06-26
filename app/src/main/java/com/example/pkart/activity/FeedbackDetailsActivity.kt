package com.example.pkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pkart.R
import com.example.pkart.model.FeedbackModel
import com.google.firebase.database.FirebaseDatabase

class FeedbackDetailsActivity : AppCompatActivity() {

    private lateinit var tvFeedbackId: TextView
    private lateinit var tvFeedbackName: TextView
    private lateinit var tvFeedbackGrade: TextView
    private lateinit var tvFeedbackFeedback: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_details)

        initView()
        setValuesToView()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("feedbackId").toString(),
                intent.getStringExtra("feedbackName").toString()
            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("feedbackId").toString()
            )
        }
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Feedback").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Feedback data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this,FeedbackFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {
                error -> Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(feedbackId: String, feedbackName: String) {

        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.feedback_update_dialog,null)

        mDialog.setView(mDialogView)

        val etFeedbackName = mDialogView.findViewById<EditText>(R.id.etFeedbackName)
        val etFeedbackGrade = mDialogView.findViewById<EditText>(R.id.etFeedbackGrade)
        val etFeedbackFeedback = mDialogView.findViewById<EditText>(R.id.etFeedbackFeedback)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateFeedbackData)

        etFeedbackName.setText(intent.getStringExtra("feedbackName").toString())
        etFeedbackGrade.setText(intent.getStringExtra("feedbackGrade").toString())
        etFeedbackFeedback.setText(intent.getStringExtra("feedbackFeedback").toString())

        mDialog.setTitle("Updating $feedbackName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateFeedbackData(
                feedbackId,
                etFeedbackName.text.toString(),
                etFeedbackGrade.text.toString(),
                etFeedbackFeedback.text.toString(),
            )

            Toast.makeText(applicationContext, "Feedback Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvFeedbackName.text = etFeedbackName.text.toString()
            tvFeedbackGrade.text = etFeedbackGrade.text.toString()
            tvFeedbackFeedback.text = etFeedbackFeedback.text.toString()


            alertDialog.dismiss()
        }
    }

    private fun updateFeedbackData(
        id: String,
        name: String,
        grade: String,
        feedback: String) {

        val dbRef = FirebaseDatabase.getInstance().getReference("Feedback").child(id)
        val feedbackInfo = FeedbackModel(id,name,grade,feedback)
        dbRef.setValue(feedbackInfo)

    }

    private fun initView() {
        tvFeedbackId= findViewById(R.id.tvFeedbackId)
        tvFeedbackName= findViewById(R.id.tvFeedbackName)
        tvFeedbackGrade= findViewById(R.id.tvFeedbackGrade)
        tvFeedbackFeedback= findViewById(R.id.tvFeedbackFeedback)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToView() {
        tvFeedbackId.text = intent.getStringExtra("feedbackId")
        tvFeedbackName.text = intent.getStringExtra("feedbackName")
        tvFeedbackGrade.text = intent.getStringExtra("feedbackGrade")
        tvFeedbackFeedback.text = intent.getStringExtra("feedbackFeedback")
    }

}