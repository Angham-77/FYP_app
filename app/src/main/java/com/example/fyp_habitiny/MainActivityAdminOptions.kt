package com.example.fyp_habitiny

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivityAdminOptions : AppCompatActivity() {

    private lateinit var drawerLayoutAdmin: DrawerLayout
    private lateinit var navViewAdmin: NavigationView
    private lateinit var toolbarAdmin: Toolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_options)

        drawerLayoutAdmin = findViewById(R.id.drawer_layout_Admin)
        navViewAdmin = findViewById(R.id.nav_view_admin)
        toolbarAdmin = findViewById(R.id.toolbar_options_admin)

        // Setting up the toolbar as the action bar
        setSupportActionBar(toolbarAdmin)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.more)

        toolbarAdmin.setNavigationOnClickListener {
            drawerLayoutAdmin.openDrawer(GravityCompat.START)
        }

        //nav
        navViewAdmin.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item clicks
            when (menuItem.itemId) {
                R.id.nav_settings_admin -> {
                    val intent = Intent(this@MainActivityAdminOptions, MainActivityAdminLogin::class.java)
                    startActivity(intent)
                }

            }
            drawerLayoutAdmin.closeDrawer(GravityCompat.START)
            true
        }
    }

    fun goToAdminFeedback(view: View){
        val intent = Intent(this, MainActivityAdminFeedback::class.java)
        startActivity(intent)
    }
    fun goToPreEditUser(view: View){
        val intent = Intent(this, MainActivityAdminEditUserPre::class.java)
        startActivity(intent)
    }
    fun addAnewUser(view: View) {
        val intent = Intent(this, MainActivityAddNewUserByAdmin::class.java)
        startActivity(intent)
    }
    fun addAnewRecoHabit(view: View) {
        val intent = Intent(this, MainActivityAdminAddRecoHabit::class.java)
        startActivity(intent)
    }
    fun viewHabitButtonOptions(view: View) {
        val intent = Intent(this, MainActivityAdminRecoHabitEdit::class.java)
        startActivity(intent)
    }
    fun addAnewMoto(view: View) {
        val intent = Intent(this, MainActivityAdminMotoInput::class.java)
        startActivity(intent)
    }

}