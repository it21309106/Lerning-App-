package com.example.pkart.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.pkart.R
import com.example.pkart.activity.MyNoteActivity
import com.example.pkart.activity.MyNoteFetchingActivity


class MyNoteFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_note, container, false)

        val createNote = view.findViewById<Button>(R.id.createbtn)
        createNote.setOnClickListener {
            val intent = Intent(activity, MyNoteActivity::class.java)
            startActivity(intent)
        }

        val viewNote = view.findViewById<Button>(R.id.viewbtn)
        viewNote.setOnClickListener {
            val intent = Intent(activity, MyNoteFetchingActivity::class.java)
            startActivity(intent)
        }


        return view

    }


}