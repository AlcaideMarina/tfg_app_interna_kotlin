package com.example.hueverianieto.ui.views

import android.os.Build
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.ActivityMainBinding
import com.example.hueverianieto.ui.views.home.HomeFragment
import com.example.hueverianieto.ui.views.usersandclients.UsersAndClientsFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    lateinit var internalUserData: InternalUserData

    override fun setUp() {
        internalUserData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("internal_user_data", InternalUserData::class.java)!!
        } else {
            intent.getParcelableExtra<InternalUserData>("internal_user_data")!!
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(this.binding.toolbar)
        this.binding.navView.setNavigationItemSelectedListener(this)

        toggle = ActionBarDrawerToggle(
            this@MainActivity,
            this.binding.drawerLayout,
            this.binding.toolbar,
            R.string.open_lateral_menu,
            R.string.close_lateral_menu
        )
        this.binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment()).commit()
        this.binding.navView.setCheckedItem(R.id.homeFragment)

        /*if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_home)
        }


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

        this.binding.navigationView.itemIconTintList = null*/
    }

    override fun configureUI() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
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

    }*/

    /*private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit()
        this.binding.drawerLayout.closeDrawers()
        this.binding.topBarText.text = title
    }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.home_bottom_menu -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            R.id.users_bottom_menu -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, UsersAndClientsFragment()).commit()
        }
        this.binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onBackPressed() {
        if (this.binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}