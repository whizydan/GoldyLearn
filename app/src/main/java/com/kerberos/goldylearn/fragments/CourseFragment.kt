package com.kerberos.goldylearn.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kerberos.goldylearn.R
import com.kerberos.goldylearn.adapters.CategoriesAdapter
import com.kerberos.goldylearn.adapters.CourseAdapter
import com.kerberos.goldylearn.database.DatabaseHandler
import com.kerberos.goldylearn.models.Course
import com.kerberos.goldylearn.models.LearningProgress

class CourseFragment(private var control:String? = null) : Fragment(),CategoriesAdapter.OnItemClickListener {
    private var courses:RecyclerView? = null
    private var courseList:List<Course>? = null
    private var adapter: CourseAdapter? = null
    private var categoriesList:List<LearningProgress>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchField = view.findViewById<TextInputLayout>(R.id.searchText)
        val searchValue = view.findViewById<TextInputEditText>(R.id.searchValue)
        val categories = view.findViewById<RecyclerView>(R.id.categories)
        courses = view.findViewById(R.id.courses)
        val db = DatabaseHandler(requireContext())
        courseList = db.getAllCourses()
        categoriesList = db.getProgress()
        courseList?.let { CourseAdapter(requireContext(), it) }
        val profileImage = view.findViewById<ImageView>(R.id.profile)

        profileImage.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentHolder,AccountFragment()).commit()
        }

        if(!control.isNullOrEmpty()){
            searchField.requestFocus()
        }

        // Inside onViewCreated
        searchValue.addTextChangedListener { editable ->
            val searchText = editable.toString().trim()
            filter(searchText)
        }


        categories.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        categories.adapter = CategoriesAdapter(this,categoriesList!!)

        courses?.adapter = CourseAdapter(requireContext(), courseList!!)
        courses?.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

    }
    private fun filter(string: String){
        val filteredList = courseList?.filter { course ->
            course.name?.contains(string, ignoreCase = true) == true
        }
        if (string == ""){
            courses?.adapter = CourseAdapter(requireContext(), courseList!!)
        }
        courses?.adapter = filteredList?.let { CourseAdapter(requireContext(), it) }
    }
    private fun filterByCategory(string: String){
        val filteredList = courseList?.filter { course ->
            course.category?.contains(string, ignoreCase = true) == true
        }
        if (string == ""){
            courses?.adapter = CourseAdapter(requireContext(), courseList!!)
        }
        courses?.adapter = filteredList?.let { CourseAdapter(requireContext(), it) }
    }

    override fun onItemClick(position: Int) {
        categoriesList?.get(position)?.category?.let { filterByCategory(it) }
    }
}