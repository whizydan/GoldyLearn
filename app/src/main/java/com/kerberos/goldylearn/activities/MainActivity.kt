package com.kerberos.goldylearn.activities// OnboardingActivity.kt
import OnboardingAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.kerberos.goldylearn.R
import com.kerberos.goldylearn.database.DatabaseHandler
import com.kerberos.goldylearn.models.OnboardingItem
import com.kerberos.goldylearn.utils.TinyDB
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var onboardingAdapter: OnboardingAdapter

    private val onboardingItems = listOf(
        OnboardingItem("Smartphone Classes By Ginasimran for Purpose of Capstone FYP", "Free course for you to learn using smartphone with ease",
            R.drawable.screen1
        ),
        OnboardingItem("Quick and easy learning", "Easy and fast learning at any time to help you improve smartphone skills",
            R.drawable.screen3
        ),
        OnboardingItem("Create your own study plan", "Study according to the study plan, make study more motivated",
            R.drawable.screen4
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val signIn = findViewById<MaterialButton>(R.id.sign_in);
        val signUP = findViewById<MaterialButton>(R.id.sign_up);
        val auth = FirebaseAuth.getInstance()
        val db = DatabaseHandler(this)
        val tinyDb = TinyDB(this)
        val firstRun = tinyDb.getString("firstRun")
        if(firstRun != "no"){
            db.insertCourses()
        }


        if(auth.currentUser?.uid != null){
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }

        signUP.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
        signIn.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        viewPager = findViewById(R.id.viewPager)
        onboardingAdapter = OnboardingAdapter(onboardingItems)
        viewPager.adapter = onboardingAdapter

        // Auto-scroll with a timer
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (viewPager.currentItem < onboardingItems.size - 1) {
                        viewPager.currentItem = viewPager.currentItem + 1
                    } else {
                        viewPager.currentItem = 0
                    }
                }
            }
        }, 6000, 3000)
    }
}
