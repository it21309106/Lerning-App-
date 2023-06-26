package com.example.pkart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pkart.R
import com.example.pkart.model.HelpModel


class HelpAdapter (private val helpList : ArrayList<HelpModel>):
    RecyclerView.Adapter<HelpAdapter.ViewHolder>(){

    private lateinit var hListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        hListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.help_list_item,parent,false)
        return ViewHolder(itemView, hListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentHelp = helpList[position]
        holder.tvHelpQuestion.text = currentHelp.helpQuestion
    }

    override fun getItemCount(): Int {
        return helpList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val tvHelpQuestion : TextView = itemView.findViewById(R.id.tvHelpQuestion)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

}