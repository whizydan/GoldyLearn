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

class CategoriesAdapter(private val onItemClickListener:OnItemClickListener,private val itemList: List<LearningProgress>) :
    RecyclerView.Adapter<CategoriesAdapter.LearningPathViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningPathViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.categories, parent, false)
        return LearningPathViewHolder(view)
    }

    override fun onBindViewHolder(holder: LearningPathViewHolder, position: Int) {
        val currentProgressItem = itemList[position]
        holder.categoryName.text = currentProgressItem.category

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }
    }
    interface OnItemClickListener{
        fun onItemClick(position:Int)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class LearningPathViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.category_name)
    }

}
