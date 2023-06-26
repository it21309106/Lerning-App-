package com.example.pkart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pkart.R
import com.example.pkart.model.NoteModel

class NoteAdapter (private val noteList : ArrayList<NoteModel>):
RecyclerView.Adapter<NoteAdapter.ViewHolder>(){

    private lateinit var nListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        nListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_list_item,parent,false)
        return ViewHolder(itemView, nListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNote = noteList[position]
        holder.tvNoteTitle.text = currentNote.noteTitle
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val tvNoteTitle : TextView = itemView.findViewById(R.id.tvNoteTitle)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

}