package com.example.hueverianieto.ui.views.internalusers

import android.os.Build
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.ActivityAllInternalUsersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllInternalUsersActivity : BaseActivity() {

    private lateinit var binding: ActivityAllInternalUsersBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var currentUserData: InternalUserData

    override fun setUp() {
        currentUserData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("current_user_data", InternalUserData::class.java)!!
        } else {
            intent.getParcelableExtra<InternalUserData>("current_user_data")!!
        }

        this.binding = ActivityAllInternalUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(this.binding.topBar)
        navController = binding.fragmentContainerView.getFragment<NavHostFragment>().navController
        this.binding.topBar.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.allInternalUsersFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
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

    fun getToolbar() : Toolbar {
        return this.binding.topBar
    }

}