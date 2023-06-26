package com.example.pkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.pkart.R
import com.example.pkart.model.CheckoutModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddressActivity : AppCompatActivity() {

    private lateinit var userEmail : EditText
    private lateinit var userName : EditText
    private lateinit var userPhoneNumber : EditText
    private lateinit var userAddress : EditText
    private lateinit var userCity : EditText
    private lateinit var userPostalCode : EditText
    private lateinit var proceed : Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        userEmail = findViewById(R.id.userEmail)
        userName = findViewById(R.id.userName)
        userPhoneNumber = findViewById(R.id.userPhoneNumber)
        userAddress = findViewById(R.id.userAddress)
        userCity = findViewById(R.id.userCity)
        userPostalCode = findViewById(R.id.userPostalCode)
        proceed= findViewById(R.id.proceed)

        dbRef = FirebaseDatabase.getInstance().getReference ("Checkout")

        proceed.setOnClickListener {
            saveCheckoutData()
        }

        val btnNext: Button =findViewById(R.id.btnNext)
        btnNext.setOnClickListener {
            val intent= Intent(this, BankCardFetchingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveCheckoutData() {
        //getting values
        val userEmail = userEmail.text.toString()
        val userName = userName.text.toString()
        val userPhoneNumber = userPhoneNumber.text.toString()
        val userAddress = userAddress.text.toString()
        val userCity = userCity.text.toString()
        val userPostalCode = userPostalCode.text.toString()

        if (userEmail.isEmpty()){
            Toast.makeText(this, "Please fill the user email", Toast.LENGTH_SHORT).show()
        }
        if (userEmail.isEmpty()){
            Toast.makeText(this, "Please fill the user name", Toast.LENGTH_SHORT).show()
        }
        if (userEmail.isEmpty()){
            Toast.makeText(this, "Please fill the phone number", Toast.LENGTH_SHORT).show()
        }
        if (userEmail.isEmpty()){
            Toast.makeText(this, "Please fill the address", Toast.LENGTH_SHORT).show()
        }
        if (userEmail.isEmpty()){
            Toast.makeText(this, "Please fill the city", Toast.LENGTH_SHORT).show()
        }
        if (userEmail.isEmpty()){
            Toast.makeText(this, "Please fill the postal code", Toast.LENGTH_SHORT).show()
        }
        
        val checkoutId = dbRef.push().key!!
        
        val checkout = CheckoutModel(checkoutId,userEmail,userName,userPhoneNumber,userAddress,userCity,userPostalCode)
        
        dbRef.child(checkoutId).setValue(checkout)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

            }.addOnFailureListener {
                err -> Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
        
        
    }
}