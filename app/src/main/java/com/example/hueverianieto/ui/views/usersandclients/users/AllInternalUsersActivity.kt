package com.example.hueverianieto.ui.views.usersandclients.users

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.databinding.ActivityAllInternalUsersBinding

class AllInternalUsersActivity : BaseActivity() {

    private lateinit var binding: ActivityAllInternalUsersBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun injection() {
        // TODO: Sin implementar
    }

    override fun setUp() {
        this.binding = ActivityAllInternalUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(this.binding.topBar)
        navController = binding.fragmentContainerView.getFragment<NavHostFragment>().navController
        this.binding.topBar.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.allInternalUsersFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        configNav("Ver usuarios internos")
    }

    override fun configureUI() {
        // TODO: Sin implementar
    }

    override fun setListeners() {
        // TODO: Sin implementar
    }

    fun configNav(title: String) {
        this.binding.topBar.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.binding.topBar.setNavigationOnClickListener { this.onBackPressedDispatcher.onBackPressed() }
    }

}