package com.example.hueverianieto.ui.views.main

import android.os.Build
import android.view.MenuItem
import android.widget.TextView
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
import com.example.hueverianieto.ui.views.main.fragments.settings.SettingsFragment
import com.example.hueverianieto.ui.views.main.fragments.usersandclients.UsersAndClientsFragment
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.Utils
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
        this.binding.settings.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment()).commit()
            this.binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        this.binding.navView.getHeaderView(0).findViewById<TextView>(R.id.name_text).text =
            currentUserData.name + " " + currentUserData.surname
        this.binding.navView.getHeaderView(0).findViewById<TextView>(R.id.role_text).text =
            applicationContext.getString(
                Utils.getKey(Constants.roles, currentUserData.position.toInt())!!
            )
    }

    override fun configureUI() {
        // Not necessary
    }

    override fun setListeners() {
        // Not necessary
    }

    override fun setObservers() {
        // Not necessary
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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

    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

}
