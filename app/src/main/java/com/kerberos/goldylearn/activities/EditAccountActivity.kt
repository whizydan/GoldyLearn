package com.kerberos.goldylearn.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.kerberos.goldylearn.R
import com.kerberos.goldylearn.dialogs.SuccessDialog


class EditAccountActivity : AppCompatActivity() {

    private var email1:String? = null
    private var password1:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)
        val email = findViewById<TextInputLayout>(R.id.textInputLayout)
        val password = findViewById<TextInputLayout>(R.id.textInputLayout2)
        val update = findViewById<MaterialButton>(R.id.materialButton)



        update.setOnClickListener{
            showInputDialog()
            when(true){
                TextUtils.isEmpty(email.editText?.text.toString())->{
                    email.error = "Enter email"
                }TextUtils.isEmpty(password.editText?.text.toString())->{
                    password.error = "Enter password"
                }email1.isNullOrBlank()->{
                    Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show()
                }password1.isNullOrBlank()->{
                    Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show()
                }
                else->{
                    val user = FirebaseAuth.getInstance().currentUser
                    val credential = EmailAuthProvider.getCredential(email1!!, password1!!)
                    user?.reauthenticate(credential)?.addOnSuccessListener {
                        user?.updatePassword(password.editText?.text.toString())!!
                            .addOnSuccessListener {
                                val dialog = SuccessDialog(this@EditAccountActivity,"Account updated successfully")
                                dialog.show()
                            }
                            .addOnFailureListener {
                                MaterialAlertDialogBuilder(this@EditAccountActivity)
                                    .setTitle("Password update failed")
                                    .setMessage(it.localizedMessage)
                            }
                    }?.addOnFailureListener {
                        MaterialAlertDialogBuilder(this@EditAccountActivity)
                            .setTitle("Authentication failed")
                            .setMessage(it.localizedMessage)
                    }
                }
            }
        }
    }
    private fun showInputDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_input, null)
        val editTextEmail = dialogView.findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = dialogView.findViewById<EditText>(R.id.editTextPassword)

        MaterialAlertDialogBuilder(this)
            .setTitle("Login")
            .setView(dialogView)
            .setPositiveButton("Login") { _, _ ->
                // Handle the positive button click
                email1 = editTextEmail.text.toString()
                password1 = editTextPassword.text.toString()

                // Process email and password here
            }.setNegativeButton("Cancel") { _, _ ->
                // Handle the negative button click
            }.show()
    }
}