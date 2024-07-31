package com.kerberos.goldylearn.adapters// MyAdapter.kt
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.kerberos.goldylearn.R
import com.kerberos.goldylearn.activities.CourseActivity
import com.kerberos.goldylearn.models.Course
import com.kerberos.goldylearn.models.LearningProgress

class CourseAdapter(private val mContext: Context, private var itemList: List<Course>) :
    RecyclerView.Adapter<CourseAdapter.LearningPathViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningPathViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.course_item, parent, false)
        return LearningPathViewHolder(view)
    }

    override fun onBindViewHolder(holder: LearningPathViewHolder, position: Int) {
        val currentProgressItem = itemList[position]
        holder.title.text = currentProgressItem.name
        holder.category.text = currentProgressItem.category
        holder.letter.text = currentProgressItem.name?.elementAt(0).toString()

        holder.itemView.setOnClickListener {
            //open the learning materials for this category only
            val intent = Intent(mContext,CourseActivity::class.java)
            intent.putExtra("id",currentProgressItem.id.toString())
            mContext.startActivity(intent)

        }
    }


    override fun getItemCount(): Int {
        return itemList.size
    }


    class LearningPathViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val letter: TextView = itemView.findViewById(R.id.letter)
        val category: TextView = itemView.findViewById(R.id.category)
    }

}
