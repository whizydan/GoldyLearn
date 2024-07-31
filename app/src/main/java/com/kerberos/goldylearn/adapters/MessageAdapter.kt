package com.kerberos.goldylearn.adapters// MyAdapter.kt
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.kerberos.goldylearn.R
import com.kerberos.goldylearn.models.Course
import com.kerberos.goldylearn.models.LearningProgress

class MessageAdapter(mContext: Context, private val itemList: List<Course>) :
    RecyclerView.Adapter<MessageAdapter.LearningPathViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningPathViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_item, parent, false)
        return LearningPathViewHolder(view)
    }

    override fun onBindViewHolder(holder: LearningPathViewHolder, position: Int) {
        val currentProgressItem = itemList[position]
        if (currentProgressItem.progress!! >= currentProgressItem.duration!!){
            holder.sender.text = "Motivator"
            holder.message.text = "Congradulations 🎊🎊 on completing ${currentProgressItem.name}"
        }else if(currentProgressItem.duration!! < 100){
            holder.sender.text = "Ideas"
            holder.message.text = "theres a course ${currentProgressItem.name} with less than two minutes,you could probably learn it in  no time at all.😁"
        }

        holder.itemView.setOnClickListener {
            //open the learning materials for this category only
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class LearningPathViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sender: TextView = itemView.findViewById(R.id.sender)
        val message: TextView = itemView.findViewById(R.id.message)
    }

}
