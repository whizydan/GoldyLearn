package com.kerberos.goldylearn

import android.app.Application
import com.kerberos.goldylearn.database.DatabaseHandler
import com.kerberos.goldylearn.utils.TinyDB

class Init : Application() {

    override fun onCreate() {
        super.onCreate()
        val db = DatabaseHandler(this)
        //db.clearStats()
    }
}