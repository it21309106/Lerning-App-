package com.example.pkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pkart.R
import com.example.pkart.adapter.NoteAdapter
import com.example.pkart.model.NoteModel
import com.google.firebase.database.*

class MyNoteFetchingActivity : AppCompatActivity() {

    private lateinit var noteRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var noteList: ArrayList<NoteModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_note_fetching)

        noteRecyclerView = findViewById(R.id.rvNote)
        noteRecyclerView.layoutManager= LinearLayoutManager(this)
        noteRecyclerView.setHasFixedSize(true)
        tvLoadingData= findViewById(R.id.tvLoadingData)

        noteList = arrayListOf<NoteModel>()

        getNotesData()

    }

    private fun getNotesData() {
        noteRecyclerView.visibility = View.GONE
        tvLoadingData.visibility =View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Notes")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                noteList.clear()
                if (snapshot.exists()){
                    for (noteSnap in snapshot.children){
                        val noteData = noteSnap.getValue(NoteModel::class.java)
                        noteList.add(noteData!!)
                    }
                    val nAdapter = NoteAdapter(noteList)
                    noteRecyclerView.adapter = nAdapter

                    nAdapter.setOnItemClickListener(object : NoteAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@MyNoteFetchingActivity,MyNoteDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("noteId",noteList[position].noteId)
                            intent.putExtra("noteTitle",noteList[position].noteTitle)
                            intent.putExtra("noteDescription",noteList[position].noteDescription)
                            startActivity(intent)
                        }

                    })

                    noteRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}