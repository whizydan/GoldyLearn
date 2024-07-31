package com.kerberos.goldylearn.activities

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.media.session.MediaController
import android.media.session.MediaSession
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.VideoView
import com.kerberos.goldylearn.R
import com.kerberos.goldylearn.database.DatabaseHandler
import com.kerberos.goldylearn.models.Course
import com.kerberos.goldylearn.utils.Utils
import java.io.File
import java.nio.file.Path

class CourseActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private var course : Course? = null
    private var db : DatabaseHandler? = null
    private var id : String? = null
    private var totalTime:Int? = 0
    private val handler = Handler()
    private val runnable = object : Runnable{
        override fun run() {
            db?.updateProgress(id!!, totalTime!!)
            handler.postDelayed(this,2000)
            totalTime = totalTime?.plus(2)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
        val webView = findViewById<WebView>(R.id.webView)
        db = DatabaseHandler(this)
        id = intent.getStringExtra("id")
        course = db?.getCourseById(id)
        val file = intent.getStringExtra("file")
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()


        if (!id.isNullOrEmpty()) {
            webView.loadUrl(course?.filename.toString())
            course = db?.getCourseById(id)
            handler.postDelayed(runnable,2000)

        }
        if(!file.isNullOrEmpty()){
            webView.loadUrl("https://www.youtube.com/watch?v=CBi0HUmTrkI&ab_channel=GordonGoose")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        if(!id.isNullOrEmpty()){
            db?.insertStat(Utils(this).getDate(),totalTime!!,course?.duration!!, id!!)
        }

    }
}