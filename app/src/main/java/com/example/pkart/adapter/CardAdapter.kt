package com.example.pkart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pkart.R
import com.example.pkart.model.CardModel
import com.example.pkart.model.NoteModel

class CardAdapter (private val cardList : ArrayList<CardModel>):
    RecyclerView.Adapter<CardAdapter.ViewHolder>(){

    private lateinit var cListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        cListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_list_item,parent,false)
        return ViewHolder(itemView, cListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNote = cardList[position]
        holder.tvCardType.text = currentNote.cardType
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val tvCardType : TextView = itemView.findViewById(R.id.tvCardType)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

}