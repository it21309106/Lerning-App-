package com.example.pkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.pkart.R

class FeedBackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back)

        val btnAddHelp: Button =findViewById(R.id.addfeedbackbtn)
        btnAddHelp.setOnClickListener {
            val intent= Intent(this, FeedbackInsertionActivity::class.java)
            startActivity(intent)
        }

        val btnViewHelp: Button =findViewById(R.id.viewfeedbackbtn)
        btnViewHelp.setOnClickListener {
            val intent= Intent(this, FeedbackFetchingActivity::class.java)
            startActivity(intent)
        }
    }
}