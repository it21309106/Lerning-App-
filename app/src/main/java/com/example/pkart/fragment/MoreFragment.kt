package com.example.pkart.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.example.pkart.R
import com.example.pkart.activity.BankCardActivity
import com.example.pkart.activity.FeedBackActivity
import com.example.pkart.activity.HelpDeskActivity
import com.example.pkart.activity.MyNoteActivity


class MoreFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_more, container, false)

        val helpbtn = view.findViewById<Button>(R.id.btnhelp)
        helpbtn.setOnClickListener {
            val intent = Intent(activity, HelpDeskActivity::class.java)
            startActivity(intent)
        }

        val feedbackbtn = view.findViewById<Button>(R.id.btnfeedback)
        feedbackbtn.setOnClickListener {
            val intent = Intent(activity, FeedBackActivity::class.java)
            startActivity(intent)
        }



        return view



    }


}