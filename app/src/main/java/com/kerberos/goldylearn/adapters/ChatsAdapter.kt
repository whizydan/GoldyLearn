package com.kerberos.goldylearn.adapters// MyAdapter.kt
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.kerberos.goldylearn.R
import com.kerberos.goldylearn.models.LearningProgress

class ChatsAdapter(private val itemList: List<Chats>) :
    RecyclerView.Adapter<ChatsAdapter.LearningPathViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningPathViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chats, parent, false)
        return LearningPathViewHolder(view)
    }

    override fun onBindViewHolder(holder: LearningPathViewHolder, position: Int) {
        val currentProgressItem = itemList[position]
        holder.email.text = currentProgressItem.email
        holder.date.text = currentProgressItem.date
        holder.message.text = currentProgressItem.message

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class LearningPathViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val email: TextView = itemView.findViewById(R.id.email)
        val date: TextView = itemView.findViewById(R.id.date)
        val message: TextView = itemView.findViewById(R.id.message)
    }

}
