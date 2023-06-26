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
import com.example.pkart.model.CardModel
import com.google.firebase.database.FirebaseDatabase

class BankCardDetailsActivity : AppCompatActivity() {

    private lateinit var tvCardId: TextView
    private lateinit var tvCardType: TextView
    private lateinit var tvCardNumber: TextView
    private lateinit var tvCardYear: TextView
    private lateinit var tvCardCvc: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_card_details)

        initView()
        setValuesToView()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("cardId").toString(),
                intent.getStringExtra("cardType").toString()
            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("cardId").toString()
            )
        }

        val btnPay: Button =findViewById(R.id.btnPay)
        btnPay.setOnClickListener {
            val intent= Intent(this, OTPActivity::class.java)
            startActivity(intent)
        }
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("CardDetails").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Card data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this,BankCardFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {
                error -> Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(cardId: String, cardType: String) {

        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.card_update_dialog,null)

        mDialog.setView(mDialogView)

        val etCardType = mDialogView.findViewById<EditText>(R.id.etCardType)
        val etCardNumber = mDialogView.findViewById<EditText>(R.id.etCardNumber)
        val etCardYear = mDialogView.findViewById<EditText>(R.id.etCardYear)
        val etCardCvc = mDialogView.findViewById<EditText>(R.id.etCardCvc)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etCardType.setText(intent.getStringExtra("cardType").toString())
        etCardNumber.setText(intent.getStringExtra("cardNumber").toString())
        etCardYear.setText(intent.getStringExtra("cardYear").toString())
        etCardCvc.setText(intent.getStringExtra("cardCvc").toString())

        mDialog.setTitle("Updating $cardType Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateCardData(
                cardId,
                etCardType.text.toString(),
                etCardNumber.text.toString(),
                etCardYear.text.toString(),
                etCardCvc.text.toString(),
            )

            Toast.makeText(applicationContext, "Card Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvCardType.text = etCardType.text.toString()
            tvCardNumber.text = etCardNumber.text.toString()
            tvCardYear.text = etCardYear.text.toString()
            tvCardCvc.text = etCardCvc.text.toString()


            alertDialog.dismiss()
        }
    }

    private fun updateCardData(
        id: String,
        type: String,
        number: String,
        year: String,
        cvc: String) {

        val dbRef = FirebaseDatabase.getInstance().getReference("CardDetails").child(id)
        val cardInfo = CardModel(id,type,number,year,cvc)
        dbRef.setValue(cardInfo)

    }

    private fun initView() {
        tvCardId= findViewById(R.id.tvCardId)
        tvCardType= findViewById(R.id.tvCardType)
        tvCardNumber= findViewById(R.id.tvCardNumber)
        tvCardYear= findViewById(R.id.tvExpYear)
        tvCardCvc= findViewById(R.id.tvCardCvc)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToView() {
        tvCardId.text = intent.getStringExtra("cardId")
        tvCardType.text = intent.getStringExtra("cardType")
        tvCardNumber.text = intent.getStringExtra("cardNumber")
        tvCardYear.text = intent.getStringExtra("cardYear")
        tvCardCvc.text = intent.getStringExtra("cardCvc")
    }

}