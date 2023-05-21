package org.d3if0119.pomodoroappnew

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {


    private val splashTimeOut: Long = 3000 // Splash screen timeout duration in milliseconds

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the splash screen layout as the content view
        setContentView(R.layout.splash_screen)

        // Determine the background color based on the current theme
        val backgroundColor = if (resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            // Dark theme
            getColor(R.color.black)
        } else {
            // Light theme
            getColor(R.color.white)
        }

        // Set the background color of the root layout in the splash screen layout
        findViewById<View>(R.id.splash_screen_background).setBackgroundColor(backgroundColor)

        // Create a handler to delay the execution of code
        Handler().postDelayed({
            // Start the main activity after the splash screen timeout
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the splash screen activity
        }, splashTimeOut)
    }
}