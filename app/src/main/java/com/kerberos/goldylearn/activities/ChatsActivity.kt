package com.kerberos.goldylearn.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kerberos.goldylearn.R
import com.kerberos.goldylearn.adapters.Chats
import com.kerberos.goldylearn.adapters.ChatsAdapter
import com.kerberos.goldylearn.utils.Utils
import java.util.Calendar

class ChatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)
        val send = findViewById<MaterialButton>(R.id.send)
        val chats = findViewById<RecyclerView>(R.id.chats)
        val message = findViewById<TextInputLayout>(R.id.message)
        val db = FirebaseDatabase.getInstance().reference.child("groupchat")
        val email = FirebaseAuth.getInstance().currentUser?.email
        val groupMessages = ArrayList<Chats>()
        val timeStamp =

        db.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                groupMessages.clear()
                snapshot.children.forEach{
                    val msg = it.getValue(Chats::class.java)
                    groupMessages.add(msg!!)
                }
                chats.adapter = ChatsAdapter(groupMessages)
                chats.layoutManager = LinearLayoutManager(this@ChatsActivity,LinearLayoutManager.VERTICAL,false)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ChatsActivity,error.details,Toast.LENGTH_LONG).show()
            }

        })

        send.setOnClickListener {
            if(TextUtils.isEmpty(message.editText?.text.toString().trim())){
                message.error = "Enter your message"
            }else{
                message.error = ""
                val msg = Chats(
                    email.toString(),
                    message.editText?.text.toString(),
                    Utils(this).getDate()
                )
                db.child(timeStamp).setValue(msg)
                    .addOnFailureListener {
                        Toast.makeText(this@ChatsActivity,"message failed: ${it.localizedMessage}",Toast.LENGTH_LONG).show()
                    }
            }
        }

    }
}