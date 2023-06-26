package com.example.pkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pkart.R
import com.example.pkart.adapter.CardAdapter
import com.example.pkart.model.CardModel
import com.google.firebase.database.*

class BankCardFetchingActivity : AppCompatActivity() {

    private lateinit var cardRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var cardList: ArrayList<CardModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_card_fetching)

        cardRecyclerView = findViewById(R.id.rvCard)
        cardRecyclerView.layoutManager= LinearLayoutManager(this)
        cardRecyclerView.setHasFixedSize(true)
        tvLoadingData= findViewById(R.id.tvLoadingData)

        cardList = arrayListOf<CardModel>()

        getCardsData()

    }

    private fun getCardsData() {
        cardRecyclerView.visibility = View.GONE
        tvLoadingData.visibility =View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("CardDetails")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                cardList.clear()
                if (snapshot.exists()){
                    for (cardSnap in snapshot.children){
                        val cardData = cardSnap.getValue(CardModel::class.java)
                        cardList.add(cardData!!)
                    }
                    val cAdapter = CardAdapter(cardList)
                    cardRecyclerView.adapter = cAdapter

                    cAdapter.setOnItemClickListener(object : CardAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@BankCardFetchingActivity,BankCardDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("cardId",cardList[position].cardId)
                            intent.putExtra("cardType",cardList[position].cardType)
                            intent.putExtra("cardNumber",cardList[position].cardNumber)
                            intent.putExtra("cardYear",cardList[position].cardYear)
                            intent.putExtra("cardCvc",cardList[position].cardCvc)
                            startActivity(intent)
                        }

                    })

                    cardRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}