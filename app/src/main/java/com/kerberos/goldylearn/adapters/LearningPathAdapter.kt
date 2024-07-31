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

class LearningPathAdapter(mContext: Context,private val itemList: List<LearningProgress>) :
    RecyclerView.Adapter<LearningPathAdapter.LearningPathViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningPathViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.learning_path, parent, false)
        return LearningPathViewHolder(view)
    }

    override fun onBindViewHolder(holder: LearningPathViewHolder, position: Int) {
        val currentProgressItem = itemList[position]
        holder.categoryName.text = currentProgressItem.category
        holder.totalProgressPerCategory.progress = currentProgressItem.progress!!
        holder.totalCategories.text = currentProgressItem.totalCategories.toString()
        holder.fullyCompletedCategories.text = currentProgressItem.completedCategories.toString()

        holder.itemView.setOnClickListener {
            //open the learning materials for this category only
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class LearningPathViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        val totalProgressPerCategory: CircularProgressIndicator = itemView.findViewById(R.id.circularProgressIndicator)
        val fullyCompletedCategories: TextView = itemView.findViewById(R.id.textView19)
        val totalCategories: TextView = itemView.findViewById(R.id.textView18)
    }

}
