package com.kerberos.goldylearn.utils

import android.content.Context
import android.widget.Toast
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.Calendar

class Utils(var context: Context) {

    public fun getAuth():FirebaseAuth
    {
        return FirebaseAuth.getInstance()
    }
    public fun getDate(): String {
        return Calendar.getInstance().time.toString()
    }
    public fun uploadDatabase(database:File):Boolean?
    {
        var returnValue = false
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val storagePath = "backups/${userId.toString()}"
        val fileReference: StorageReference = FirebaseStorage.getInstance().reference.child(storagePath)
        fileReference.child(database.name).putFile(database.toUri())
            .addOnSuccessListener {
                returnValue = true
                Toast.makeText(context,"Backup saved",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context,e.localizedMessage,Toast.LENGTH_LONG).show()
            }
        return returnValue
    }
    fun downloadBackup():Boolean
    {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val storagePath = "backups/${userId}/leaner_database.db"
        var returnValue = false
        val storage: FirebaseStorage = FirebaseStorage.getInstance()
        val storageReference: StorageReference = storage.reference
        val fileReference: StorageReference = storageReference.child(storagePath)
        var localFile = File.createTempFile("temp","db")

        fileReference.getFile(localFile)
            .addOnSuccessListener {
                returnValue = true
                Toast.makeText(context,"Backup downloaded",Toast.LENGTH_LONG).show()
                localFile.renameTo(File("/databases/leaner_database.db"))
            }
            .addOnFailureListener { e ->
                Toast.makeText(context,e.localizedMessage,Toast.LENGTH_LONG).show()
            }
        return returnValue
    }
}