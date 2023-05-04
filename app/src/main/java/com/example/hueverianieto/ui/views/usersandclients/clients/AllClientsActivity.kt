package com.example.hueverianieto.ui.views.usersandclients.clients

import android.util.Log
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.databinding.ActivityAllClientsBinding

class AllClientsActivity : BaseActivity() {

    private lateinit var binding: ActivityAllClientsBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun setUp() {
        this.binding = ActivityAllClientsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(this.binding.topBar)
        navController = binding.fragContViewCentre.getFragment<NavHostFragment>().navController
        this.binding.topBar.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.allClientsFragment, R.id.newClientFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        configNav("Ver clientes")

    }

    override fun configureUI() {
        //
    }

    override fun setListeners() {
        // No listeners are necessary for this activity
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    fun configNav(title: String) {
        this.binding.topBar.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.binding.topBar.setNavigationOnClickListener { this.onBackPressedDispatcher.onBackPressed() }
    }

}