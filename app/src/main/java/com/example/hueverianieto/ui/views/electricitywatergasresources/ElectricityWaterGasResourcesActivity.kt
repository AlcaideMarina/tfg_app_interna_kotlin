package com.example.hueverianieto.ui.views.electricitywatergasresources

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
import com.example.hueverianieto.databinding.ActivityElectricityGasWaterResourcesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ElectricityWaterGasResourcesActivity : BaseActivity() {

    private lateinit var binding: ActivityElectricityGasWaterResourcesBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var currentUserData: InternalUserData

    override fun setUp() {
        currentUserData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("current_user_data", InternalUserData::class.java)!!
        } else {
            intent.getParcelableExtra<InternalUserData>("current_user_data")!!
        }

        this.binding = ActivityElectricityGasWaterResourcesBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        setSupportActionBar(this.binding.topBar)
        navController = this.binding.fragmentContainerView.getFragment<NavHostFragment>().navController
        this.binding.topBar.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.layout.fragment_all_electricity_water_gas_resources)
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
