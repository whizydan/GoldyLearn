package com.kerberos.goldylearn.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.kerberos.goldylearn.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var auth = FirebaseAuth.getInstance()
        var email = findViewById<TextInputLayout>(R.id.textInputLayout)
        var password = findViewById<TextInputLayout>(R.id.textInputLayout2)
        var checkBoolean = findViewById<MaterialCheckBox>(R.id.materialCheckBox)
        var signIn = findViewById<TextView>(R.id.sign_up)
        var signUp = findViewById<MaterialButton>(R.id.materialButton)
        signUp.isEnabled = false

        signIn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        checkBoolean.setOnCheckedChangeListener { buttonView, isChecked ->
            signUp.isEnabled = isChecked
        }
        signUp.setOnClickListener {
            when(true){
                TextUtils.isEmpty(email.editText?.text.toString())->{
                    email.error = "Enter email"
                }TextUtils.isEmpty(password.editText?.text.toString())->{
                    password.error = "Enter password"
                }
                else -> {
                    auth.createUserWithEmailAndPassword(email.editText?.text.toString(),password.editText?.text.toString())
                        .addOnSuccessListener {
                            startActivity(Intent(this@RegisterActivity,HomeActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener {
                            MaterialAlertDialogBuilder(this@RegisterActivity)
                                .setTitle("Failed to register")
                                .setMessage(it.localizedMessage)
                                .show()
                        }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}