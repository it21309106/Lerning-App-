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
import com.example.pkart.model.HelpModel
import com.google.firebase.database.FirebaseDatabase

class HelpDeskDetailsActivity : AppCompatActivity() {

    private lateinit var tvHelpId: TextView
    private lateinit var tvHelpSubject: TextView
    private lateinit var tvHelpQuestion: TextView
    private lateinit var tvHelpEmail: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_desk_details)

        initView()
        setValuesToView()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("helpId").toString(),
                intent.getStringExtra("helpQuestion").toString()
            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("helpId").toString()
            )
        }
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("HelpDesk").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Help data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this,HelpFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {
                error -> Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(helpId: String, helpQuestion: String) {

        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.help_update_dialog,null)

        mDialog.setView(mDialogView)

        val etHelpSubject = mDialogView.findViewById<EditText>(R.id.etHelpSubject)
        val etHelpQuestion = mDialogView.findViewById<EditText>(R.id.etHelpQuestion)
        val etHelpEmail = mDialogView.findViewById<EditText>(R.id.etHelpEmail)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateHelpData)

        etHelpSubject.setText(intent.getStringExtra("helpSubject").toString())
        etHelpQuestion.setText(intent.getStringExtra("helpQuestion").toString())
        etHelpEmail.setText(intent.getStringExtra("helpEmail").toString())

        mDialog.setTitle("Updating $helpQuestion Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateHelpData(
                helpId,
                etHelpSubject.text.toString(),
                etHelpQuestion.text.toString(),
                etHelpEmail.text.toString(),
            )

            Toast.makeText(applicationContext, "Help Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvHelpSubject.text = etHelpSubject.text.toString()
            tvHelpQuestion.text = etHelpQuestion.text.toString()
            tvHelpEmail.text = etHelpEmail.text.toString()


            alertDialog.dismiss()
        }
    }

    private fun updateHelpData(
        id: String,
        subject: String,
        question: String,
        email: String) {

        val dbRef = FirebaseDatabase.getInstance().getReference("HelpDesk").child(id)
        val helpInfo = HelpModel(id,subject,question,email)
        dbRef.setValue(helpInfo)

    }

    private fun initView() {
        tvHelpId= findViewById(R.id.tvHelpId)
        tvHelpSubject= findViewById(R.id.tvHelpSubject)
        tvHelpQuestion= findViewById(R.id.tvHelpQuestion)
        tvHelpEmail= findViewById(R.id.tvHelpEmail)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToView() {
        tvHelpId.text = intent.getStringExtra("helpId")
        tvHelpSubject.text = intent.getStringExtra("helpSubject")
        tvHelpQuestion.text = intent.getStringExtra("helpQuestion")
        tvHelpEmail.text = intent.getStringExtra("helpEmail")
    }

}