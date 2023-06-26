package com.example.pkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.pkart.R
import com.example.pkart.fragment.HomeFragment

class PaymentSuccessfulActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_successful)

        val btnGoHome: Button =findViewById(R.id.btnGoHome)
        btnGoHome.setOnClickListener {
            val intent= Intent(this, AddressActivity::class.java)
            startActivity(intent)
        }
    }
}