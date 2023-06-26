package com.example.pkart.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.pkart.R
import com.example.pkart.activity.BankCardActivity
import com.example.pkart.activity.BankCardFetchingActivity
import com.example.pkart.activity.MyNoteActivity
import com.example.pkart.activity.MyNoteFetchingActivity


class WishlistFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)

        val createCard = view.findViewById<Button>(R.id.createbtn)
        createCard.setOnClickListener {
            val intent = Intent(activity, BankCardActivity::class.java)
            startActivity(intent)
        }

        val viewCard = view.findViewById<Button>(R.id.viewbtn)
        viewCard.setOnClickListener {
            val intent = Intent(activity, BankCardFetchingActivity::class.java)
            startActivity(intent)
        }


        return view

    }


}