package com.example.hueverianieto.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected abstract fun configureUI()

    protected abstract fun setObservers()

    protected abstract fun setListeners()

    protected abstract fun updateUI(state: BaseState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(
            this.javaClass.simpleName,
            "Calling fragment onCreate(): " + this.javaClass.simpleName
        )
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureUI()
        setListeners()
    }

    override fun onStart() {
        super.onStart()
        Log.d(this.javaClass.simpleName, "Calling fragment onStart(): " + this.javaClass.simpleName)
    }

    override fun onResume() {
        super.onResume()
        Log.d(
            this.javaClass.simpleName,
            "Calling fragment onResume(): " + this.javaClass.simpleName
        )
    }

    override fun onStop() {
        super.onStop()
        Log.d(this.javaClass.simpleName, "Calling fragment onStop(): " + this.javaClass.simpleName)
    }

}