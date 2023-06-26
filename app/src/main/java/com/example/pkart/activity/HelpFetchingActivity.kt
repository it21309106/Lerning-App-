package com.example.pkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pkart.R
import com.example.pkart.adapter.HelpAdapter
import com.example.pkart.model.HelpModel
import com.google.firebase.database.*

class HelpFetchingActivity : AppCompatActivity() {

    private lateinit var helpRecyclerView: RecyclerView
    private lateinit var tvLoadingHelpData: TextView
    private lateinit var helpList: ArrayList<HelpModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_fetching)

        helpRecyclerView = findViewById(R.id.rvHelp)
        helpRecyclerView.layoutManager= LinearLayoutManager(this)
        helpRecyclerView.setHasFixedSize(true)
        tvLoadingHelpData= findViewById(R.id.tvLoadingData)

        helpList = arrayListOf<HelpModel>()

        getHelpsData()
    }

    private fun getHelpsData() {
        helpRecyclerView.visibility = View.GONE
        tvLoadingHelpData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("HelpDesk")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                helpList.clear()
                if (snapshot.exists()){
                    for (helpSnap in snapshot.children){
                        val helData = helpSnap.getValue(HelpModel::class.java)
                        helpList.add(helData!!)
                    }
                    val hAdapter = HelpAdapter(helpList)
                    helpRecyclerView.adapter = hAdapter

                    hAdapter.setOnItemClickListener(object :HelpAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent (this@HelpFetchingActivity,HelpDeskDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("helpId",helpList[position].helpId)
                            intent.putExtra("helpSubject",helpList[position].helpSubject)
                            intent.putExtra("helpQuestion",helpList[position].helpQuestion)
                            intent.putExtra("helpEmail",helpList[position].helpEmail)
                            startActivity(intent)
                        }

                    })

                    helpRecyclerView.visibility = View.VISIBLE
                    tvLoadingHelpData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}