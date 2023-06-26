package com.example.pkart.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.pkart.R
import com.example.pkart.model.CardModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BankCardActivity : AppCompatActivity() {

    private lateinit var cardType : EditText
    private lateinit var cardNumber : EditText
    private lateinit var cardYear : EditText
    private lateinit var cardCvc : EditText
    private lateinit var btnSave : Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_card)

        cardType = findViewById(R.id.cardType)
        cardNumber = findViewById(R.id.cardNumber)
        cardYear = findViewById(R.id.cardYear)
        cardCvc = findViewById(R.id.cardCvc)
        btnSave = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("CardDetails")

        btnSave.setOnClickListener {
            saveCardData()
        }

    }

    private fun saveCardData() {
        //getting values
        val cardType = cardType.text.toString()
        val cardNumber = cardNumber.text.toString()
        val cardYear = cardYear.text.toString()
        val cardCvc = cardCvc.text.toString()



        val cardId = dbRef.push().key!!

        val card = CardModel(cardId,cardType,cardNumber,cardYear,cardCvc)

        dbRef.child(cardId).setValue(card)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                    err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}