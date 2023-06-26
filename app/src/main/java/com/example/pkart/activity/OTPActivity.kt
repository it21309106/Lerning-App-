package com.example.pkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.pkart.R

class OTPActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpactivity)

        val btnVerify: Button =findViewById(R.id.btnverify)
        btnVerify.setOnClickListener {
            val intent= Intent(this, PaymentSuccessfulActivity::class.java)
            startActivity(intent)
        }
    }
}