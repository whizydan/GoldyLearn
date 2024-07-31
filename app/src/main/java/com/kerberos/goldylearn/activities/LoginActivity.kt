package com.kerberos.goldylearn.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.kerberos.goldylearn.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val email = findViewById<TextInputLayout>(R.id.textInputLayout)
        val password = findViewById<TextInputLayout>(R.id.textInputLayout2)
        val auth = FirebaseAuth.getInstance()
        val resetPassword = findViewById<TextView>(R.id.forgot_password)
        val signUp = findViewById<TextView>(R.id.sign_up)
        val signIn = findViewById<MaterialButton>(R.id.materialButton)

        signIn.setOnClickListener {
            when(true){
                TextUtils.isEmpty(email.editText?.text.toString())->{
                    email.error = "Enter email"
                }

                TextUtils.isEmpty(password.editText?.text.toString())->{
                password.error = "Enter password"
            }
                else -> {
                    auth.signInWithEmailAndPassword(email.editText?.text.toString(),password.editText?.text.toString())
                        .addOnSuccessListener {
                            startActivity(Intent(this@LoginActivity,RestoreActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener {
                            MaterialAlertDialogBuilder(this@LoginActivity)
                                .setTitle("Login Failed")
                                .setMessage(it.localizedMessage)
                                .show()
                        }
                }
            }
        }
        resetPassword.setOnClickListener {
            startActivity(Intent(this,ForgotPasswordActivity::class.java))
        }
        signUp.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }
}