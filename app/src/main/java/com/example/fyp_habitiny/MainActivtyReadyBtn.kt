package com.example.fyp_habitiny


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivtyReadyBtn : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar_options)

        // Setting up the toolbar as the action bar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.more)

        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item clicks
            when (menuItem.itemId) {
                R.id.nav_home_side -> {
                    val intent = Intent(this@MainActivtyReadyBtn, MainActivityAdminEditUserPre::class.java)
                    startActivity(intent)
                }
                R.id.nav_settings -> {
                    val intent = Intent(this@MainActivtyReadyBtn, MainActivity::class.java)
                    startActivity(intent)
                }
                // Additional cases for other menu items if necessary
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    fun readyButton(view: View) {
        val intent = Intent(this, MainActivtyMyHabit::class.java)
        startActivity(intent)
    }

    fun addHabitButton2(view: View) {
        val intent = Intent(this, MainActivityAddNewHabit::class.java)
        startActivity(intent)
    }

    fun viewRecoHabits(view: View) {
        val intent = Intent(this, MainActivityPreCreatedHabits::class.java)
        startActivity(intent)
    }

    fun GoToMotoSpace(view: View) {
        val intent = Intent(this, MainActivtyMotoSpace::class.java)
        startActivity(intent)
    }

    fun GoToFeedback(view: View) {
        val intent = Intent(this, MainActivityFeedback::class.java)
        startActivity(intent)
    }

    fun GoToArchive(view: View) {
        val intent = Intent(this, MainActivityDoneHabit::class.java)
        startActivity(intent)
    }
}
