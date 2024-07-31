package com.kerberos.goldylearn.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.TextView
import androidx.core.text.toSpannable
import com.kerberos.goldylearn.R
import java.io.InputStream

class PrivacyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        val privacyPolicy = findViewById<TextView>(R.id.privacyPolicy)

        val obj = resources.openRawResource(R.raw.privacy)
        val bufferedReader = obj.bufferedReader()
        val spannableString = SpannableStringBuilder()
        bufferedReader.readLines().forEach {
            spannableString.append(it)
        }
        privacyPolicy.text = spannableString.toSpannable()
    }
}