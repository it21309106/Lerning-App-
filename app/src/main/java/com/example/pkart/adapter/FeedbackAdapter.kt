package com.example.pkart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pkart.R
import com.example.pkart.model.FeedbackModel



class FeedbackAdapter (private val feedbackList : ArrayList<FeedbackModel>):
    RecyclerView.Adapter<FeedbackAdapter.ViewHolder>(){

    private lateinit var fListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        fListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.feedback_list_item,parent,false)
        return ViewHolder(itemView, fListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFeedback = feedbackList[position]
        holder.tvFeedbackName.text = currentFeedback.feedbackName
    }

    override fun getItemCount(): Int {
        return feedbackList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val tvFeedbackName : TextView = itemView.findViewById(R.id.tvFeedbackName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

}