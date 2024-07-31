package com.kerberos.goldylearn.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kerberos.goldylearn.R
import com.kerberos.goldylearn.fragments.AccountFragment
import com.kerberos.goldylearn.fragments.CourseFragment
import com.kerberos.goldylearn.fragments.HomeFragment
import com.kerberos.goldylearn.fragments.MessageFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val search = findViewById<FloatingActionButton>(R.id.fab)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentHolder, HomeFragment()).commit()

        search.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentHolder, CourseFragment("anything")).commit()
        }
        bottomNavigationView.setOnItemSelectedListener {
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            when (it.itemId) {
                R.id.home -> {
                    fragmentTransaction.replace(R.id.fragmentHolder, HomeFragment())
                }
                R.id.course -> {
                    fragmentTransaction.replace(R.id.fragmentHolder, CourseFragment())
                }
                R.id.message -> {
                    fragmentTransaction.replace(R.id.fragmentHolder, MessageFragment())
                }
                R.id.account -> {
                    fragmentTransaction.replace(R.id.fragmentHolder, AccountFragment())
                }
            }

            fragmentTransaction.commit()
            true
        }

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

    }
}
