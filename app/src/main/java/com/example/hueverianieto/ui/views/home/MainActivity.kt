package com.example.hueverianieto.ui.views.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.hueverianieto.R
import com.example.hueverianieto.databinding.ActivityMainBinding
import com.example.hueverianieto.ui.views.usersandclients.UsersAndClientsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggle = ActionBarDrawerToggle(
            this@MainActivity,
            this.binding.drawerLayout,
            R.string.open_lateral_menu,
            R.string.close_lateral_menu
        )
        this.binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.binding.navigationView.setNavigationItemSelectedListener {
            it.isChecked = true
            when (it.itemId) {
                R.id.home_bottom_menu -> replaceFragment(HomeFragment(), "Huevería Nieto")
                R.id.orders_bottom_menu -> Toast.makeText(
                    applicationContext,
                    "ORDERS",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.economy_bottom_menu -> Toast.makeText(
                    applicationContext,
                    "ECCONOMY",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.farm_bottom_menu -> Toast.makeText(
                    applicationContext,
                    "FARM",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.material_bottom_menu -> Toast.makeText(
                    applicationContext,
                    "MATERIAL",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.users_bottom_menu -> replaceFragment(
                    UsersAndClientsFragment(),
                    it.title.toString()
                )
            }
            true
        }
        this.binding.topBarMenuIcon.setOnClickListener {
            this.binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        this.binding.navigationView.itemIconTintList = null
        //this.binding.bottomMenu.itemIconTintList = null

    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit()
        this.binding.drawerLayout.closeDrawers()
        this.binding.topBarText.text = title
    }

}