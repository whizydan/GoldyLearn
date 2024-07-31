package com.kerberos.goldylearn.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.firebase.auth.FirebaseAuth
import com.kerberos.goldylearn.R
import com.kerberos.goldylearn.activities.ChatsActivity
import com.kerberos.goldylearn.activities.CourseActivity
import com.kerberos.goldylearn.activities.HistoryActivity
import com.kerberos.goldylearn.adapters.LearningPathAdapter
import com.kerberos.goldylearn.database.DatabaseHandler
import com.kerberos.goldylearn.models.Course
import com.kerberos.goldylearn.models.LearningProgress

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = DatabaseHandler(requireContext())
        val progress = db.getProgress()
        val stats = db.getStats()

        val horizontalScrollView = view.findViewById<HorizontalScrollView>(R.id.horizontalScrollView)
        val totalDoneTime = view.findViewById<TextView>(R.id.textView8)
        val totalTime = view.findViewById<TextView>(R.id.totalTime)
        val progressBar = view.findViewById<LinearProgressIndicator>(R.id.progress)
        val meetup = view.findViewById<MaterialButton>(R.id.meetup)
        val email = view.findViewById<TextView>(R.id.textView4)
        val profileImage = view.findViewById<ImageView>(R.id.imageView)
        horizontalScrollView.isHorizontalScrollBarEnabled = false
        val recyclerView = view.findViewById<RecyclerView>(R.id.learningPlan)
        val historyText = view.findViewById<TextView>(R.id.textView9)
        val shortFilm = view.findViewById<MaterialButton>(R.id.shortFilm)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = LearningPathAdapter(requireContext(),progress)
        email.text = "Hi, ${FirebaseAuth.getInstance().currentUser?.email}"

        totalTime.text = "/${stats[0]} mins"
        totalDoneTime.text = "${stats[1]} mins"
        if(stats[0] == 1 && stats[1] == 1){
            progressBar.progress = 0
            totalTime.text = "/0 mins"
            totalDoneTime.text = "0 mins"
        }else{
            progressBar.progress = ((stats[1].toFloat() / stats[0].toFloat())*100).toInt()
        }

        meetup.setOnClickListener {
            startActivity(Intent(requireContext(),ChatsActivity::class.java))
        }

        shortFilm.setOnClickListener {
            val intent = Intent(context,CourseActivity::class.java)
            intent.putExtra("file","take_a_break.mp4")
            startActivity(intent)
        }
        historyText.setOnClickListener {
            context?.startActivity(Intent(context,HistoryActivity::class.java))
        }
        profileImage.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentHolder, AccountFragment()).commit()
        }

    }
}