package com.kerberos.goldylearn.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kerberos.goldylearn.R
import com.kerberos.goldylearn.database.DatabaseHandler
import com.kerberos.goldylearn.utils.Utils

class RestoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore)
        val lottie = findViewById<LottieAnimationView>(R.id.backupAnimation)
        val statusText = findViewById<TextView>(R.id.textView21)
        val backupButton = findViewById<Button>(R.id.backupButton)
        val nextButton = findViewById<Button>(R.id.nextButton)
        val util = Utils(this)

        backupExists { exists ->
            if (exists) {
                statusText.text = "Backup found downloading!"
                    statusText.text = "Backup downloaded"
                    lottie.setAnimation(R.raw.tick)
                    lottie.playAnimation()
                    statusText.text = "Backup downloaded"

                backupButton.visibility = View.GONE
                nextButton.visibility = View.VISIBLE
            } else {
                backupButton.visibility = View.GONE
                nextButton.visibility = View.VISIBLE
                statusText.text = "Currently you do not have a backup! Please create a backup later."
                lottie.setAnimation(R.raw.search)
                lottie.playAnimation()
            }
        }

        nextButton.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }

        backupButton.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }
    private fun backupExists(callback: (Boolean) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val filePath = "leaner_database.db"
        val storage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference.child("backups").child(userId.toString())

        val fileReference = storageRef.child(filePath)

        fileReference.metadata
            .addOnSuccessListener {
                // The file exists
                callback.invoke(true)
            }
            .addOnFailureListener {
                // The file doesn't exist or an error occurred
                callback.invoke(false)
            }
    }
}