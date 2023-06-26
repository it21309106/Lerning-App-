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
import com.example.pkart.model.NoteModel
import com.google.firebase.database.FirebaseDatabase

class MyNoteDetailsActivity : AppCompatActivity() {

    private lateinit var tvNoteId: TextView
    private lateinit var tvNoteTitle: TextView
    private lateinit var tvNoteDescription: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detils)

        initView()
        setValuesToView()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("noteId").toString(),
                intent.getStringExtra("noteTitle").toString()
            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("noteId").toString()
            )
        }
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Notes").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Note data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this,MyNoteFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {
                error -> Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(noteId: String, noteTitle: String) {

        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.note_update_dialog,null)

        mDialog.setView(mDialogView)

        val etNoteTitle = mDialogView.findViewById<EditText>(R.id.etNoteTitle)
        val etNoteDescription = mDialogView.findViewById<EditText>(R.id.etNoteDescription)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etNoteTitle.setText(intent.getStringExtra("noteTitle").toString())
        etNoteDescription.setText(intent.getStringExtra("noteDescription").toString())

        mDialog.setTitle("Updating $noteTitle Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateNoteData(
                noteId,
                etNoteTitle.text.toString(),
                etNoteDescription.text.toString(),
            )

            Toast.makeText(applicationContext, "Note Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvNoteTitle.text = etNoteTitle.text.toString()
            tvNoteDescription.text = etNoteDescription.text.toString()


            alertDialog.dismiss()
        }
    }

    private fun updateNoteData(
        id: String,
        title: String,
        description: String) {

        val dbRef = FirebaseDatabase.getInstance().getReference("Notes").child(id)
        val empInfo = NoteModel(id,title,description)
        dbRef.setValue(empInfo)

    }

    private fun initView() {
       tvNoteId= findViewById(R.id.tvNoteId)
        tvNoteTitle= findViewById(R.id.tvNoteTitle)
        tvNoteDescription= findViewById(R.id.tvNoteDescription)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToView() {
        tvNoteId.text = intent.getStringExtra("noteId")
        tvNoteTitle.text = intent.getStringExtra("noteTitle")
        tvNoteDescription.text = intent.getStringExtra("noteDescription")
    }

}