package com.kerberos.goldylearn.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kerberos.goldylearn.R
import com.kerberos.goldylearn.adapters.CourseAdapter
import com.kerberos.goldylearn.database.DatabaseHandler

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val db = DatabaseHandler(this)
        val history = db.getHistory()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val linearLayout = findViewById<LinearLayout>(R.id.container)

        if(history.size != 0){
            linearLayout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        recyclerView.adapter = CourseAdapter(this,history)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }
}