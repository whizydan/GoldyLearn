package com.kerberos.goldylearn.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.kerberos.goldylearn.R

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        val auth = FirebaseAuth.getInstance()
        val email = findViewById<TextInputLayout>(R.id.textInputLayout)
        val reset = findViewById<MaterialButton>(R.id.materialButton)

        reset.setOnClickListener {
            if(TextUtils.isEmpty(email.editText?.text.toString()))
            {
                email.error = "Enter email"
            }else{
                auth.sendPasswordResetEmail(email.editText?.text.toString())
                    .addOnSuccessListener {
                        MaterialAlertDialogBuilder(this@ForgotPasswordActivity)
                            .setTitle("Email sent")
                            .setMessage("An email containing your password reset instructions has been sent to your inbox at ${email.editText?.text.toString()}")
                            .show()
                    }
                    .addOnFailureListener {
                        MaterialAlertDialogBuilder(this@ForgotPasswordActivity)
                            .setTitle("Email not sent")
                            .setMessage(it.localizedMessage)
                            .show()
                    }

            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}