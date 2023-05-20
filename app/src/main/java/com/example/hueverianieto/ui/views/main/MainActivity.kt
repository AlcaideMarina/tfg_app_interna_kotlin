package com.example.hueverianieto.ui.views.main

import android.os.Build
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.ActivityMainBinding
import com.example.hueverianieto.ui.views.main.fragments.ecconomy.EconomyFragment
import com.example.hueverianieto.ui.views.main.fragments.farm.FarmFragment
import com.example.hueverianieto.ui.views.main.fragments.home.HomeFragment
import com.example.hueverianieto.ui.views.main.fragments.material.MaterialFragment
import com.example.hueverianieto.ui.views.main.fragments.orderanddelivery.OrderAndDeliveryFragment
import com.example.hueverianieto.ui.views.main.fragments.usersandclients.UsersAndClientsFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private val mainViewModel: MainViewModel by viewModels()

    lateinit var currentUserData: InternalUserData

    override fun setUp() {
        currentUserData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("current_user_data", InternalUserData::class.java)!!
        } else {
            intent.getParcelableExtra<InternalUserData>("current_user_data")!!
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
        this.binding.navView.setCheckedItem(R.id.home_bottom_menu)

        this.binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            this.mainViewModel.navigateToLogin(applicationContext, this)
        }

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
            R.id.orders_bottom_menu -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OrderAndDeliveryFragment()).commit()
            R.id.economy_bottom_menu -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, EconomyFragment()).commit()
            R.id.farm_bottom_menu -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FarmFragment()).commit()
            R.id.material_bottom_menu -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MaterialFragment()).commit()
            R.id.users_bottom_menu -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, UsersAndClientsFragment()).commit()
            R.id.logout -> {

            }
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