package com.kerberos.goldylearn.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.kerberos.goldylearn.R

class SuccessDialog(context : Context,bodyText:String) : Dialog(context) {

    private var bodyText:String? = null

    init {
        this.window?.decorView?.background = null
        this.bodyText = bodyText
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_success)
        val dismissButton = findViewById<MaterialButton>(R.id.dismissButton)
        val headerImage = findViewById<ImageView>(R.id.headerImage)
        val textField = findViewById<TextView>(R.id.bodyText)

        if (!bodyText.isNullOrBlank()){
            textField.text = bodyText
        }

        dismissButton.setOnClickListener {
            this.dismiss()
        }
    }
}