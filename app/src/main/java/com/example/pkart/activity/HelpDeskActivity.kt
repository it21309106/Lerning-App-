package com.example.pkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.pkart.R

class HelpDeskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_desk)

        val btnAddHelp: Button =findViewById(R.id.addhelpbtn)
        btnAddHelp.setOnClickListener {
            val intent= Intent(this, HelpInsertionActivity::class.java)
            startActivity(intent)
        }

        val btnViewHelp: Button =findViewById(R.id.viewhelpbtn)
        btnViewHelp.setOnClickListener {
            val intent= Intent(this, HelpFetchingActivity::class.java)
            startActivity(intent)
        }
    }
}