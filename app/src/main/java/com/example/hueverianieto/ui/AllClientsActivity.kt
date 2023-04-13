package com.example.hueverianieto.ui

import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.databinding.ActivityAllClientsBinding

class AllClientsActivity : BaseActivity() {

    private lateinit var binding: ActivityAllClientsBinding

    override fun injection() {
        // TODO: Sin implementar
    }

    override fun setUp() {
        this.binding = ActivityAllClientsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun configureUI() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, AllClientsFragment()).commit()
    }

    override fun setListeners() {
        // No listeners are necessary for this activity
    }

}