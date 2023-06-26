package com.example.pkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pkart.R
import com.example.pkart.adapter.FeedbackAdapter
import com.example.pkart.model.FeedbackModel
import com.google.firebase.database.*

class FeedbackFetchingActivity : AppCompatActivity() {

    private lateinit var feedbackRecyclerView: RecyclerView
    private lateinit var tvLoadingFeedbackData: TextView
    private lateinit var feedbackList: ArrayList<FeedbackModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_fetching)

        feedbackRecyclerView = findViewById(R.id.rvFeedback)
        feedbackRecyclerView.layoutManager= LinearLayoutManager(this)
        feedbackRecyclerView.setHasFixedSize(true)
        tvLoadingFeedbackData= findViewById(R.id.tvLoadingData)

        feedbackList = arrayListOf <FeedbackModel>()

        getFeedbacksData()
    }

    private fun getFeedbacksData() {
        feedbackRecyclerView.visibility = View.GONE
        tvLoadingFeedbackData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Feedback")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                feedbackList.clear()
                if (snapshot.exists()){
                    for (feedbackSnap in snapshot.children){
                        val feedbackData = feedbackSnap.getValue(FeedbackModel::class.java)
                        feedbackList.add(feedbackData!!)
                    }
                    val fAdapter = FeedbackAdapter(feedbackList)
                    feedbackRecyclerView.adapter = fAdapter

                    fAdapter.setOnItemClickListener(object :FeedbackAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent (this@FeedbackFetchingActivity,FeedbackDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("feedbackId",feedbackList[position].feedbackId)
                            intent.putExtra("feedbackName",feedbackList[position].feedbackName)
                            intent.putExtra("feedbackGrade",feedbackList[position].feedbackGrade)
                            intent.putExtra("feedbackFeedback",feedbackList[position].feedbackFeedback)
                            startActivity(intent)
                        }

                    })

                    feedbackRecyclerView.visibility = View.VISIBLE
                    tvLoadingFeedbackData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}