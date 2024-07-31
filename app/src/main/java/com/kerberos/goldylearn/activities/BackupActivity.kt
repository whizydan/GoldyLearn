package com.kerberos.goldylearn.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.kerberos.goldylearn.R
import com.kerberos.goldylearn.database.DatabaseHandler
import com.kerberos.goldylearn.utils.Utils

class BackupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backup)
        val lottie = findViewById<LottieAnimationView>(R.id.backupAnimation)
        val backupButton = findViewById<Button>(R.id.backupButton)
        val util = Utils(this)
        var backupSaved:Boolean?

        backupButton.setOnClickListener {
            val db = DatabaseHandler(this)
            backupSaved = util.uploadDatabase(db.getDatabase())
            while(backupSaved == true){
                lottie.setAnimation(R.raw.uploading)
                lottie.playAnimation()
            }
            lottie.setAnimation(R.raw.tick)
            lottie.playAnimation()
        }
    }
}