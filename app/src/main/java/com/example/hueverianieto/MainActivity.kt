package com.example.hueverianieto

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.hueverianieto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.binding.topBarMenuIcon.setOnClickListener {
            (
                    this.binding.drawerLayout.openDrawer(GravityCompat.START)
                    )
        }
        this.binding.navigationView.itemIconTintList = null
        //this.binding.bottomMenu.itemIconTintList = null

        this.binding.apply {
            // TODO: revisar, no entiendo los attrs del toggle
            toggle = ActionBarDrawerToggle(
                this@MainActivity,
                drawerLayout,
                R.string.menu_bottom_menu,
                R.string.farm_lateral_menu
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            navigationView.setNavigationItemSelectedListener {
                // TODO: strings
                when (it.itemId) {
                    R.id.home_bottom_menu -> {
                        Toast.makeText(this@MainActivity, "Inicio", Toast.LENGTH_LONG).show()
                    }
                    R.id.orders_bottom_menu -> {
                        Toast.makeText(this@MainActivity, "Pedidos y repartos", Toast.LENGTH_LONG)
                            .show()
                    }
                    R.id.economy_bottom_menu -> {
                        Toast.makeText(this@MainActivity, "Economía", Toast.LENGTH_LONG).show()
                    }
                    R.id.farm_bottom_menu -> {
                        Toast.makeText(this@MainActivity, "Granja", Toast.LENGTH_LONG).show()
                    }
                    R.id.material_bottom_menu -> {
                        Toast.makeText(this@MainActivity, "Material", Toast.LENGTH_LONG).show()
                    }
                    R.id.users_bottom_menu -> {
                        Toast.makeText(this@MainActivity, "Usuarios", Toast.LENGTH_LONG).show()
                    }
                }
                true
            }
        }
    }

}