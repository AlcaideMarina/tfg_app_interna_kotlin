package com.example.hueverianieto.ui.views.main.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentSettingsBinding
import com.example.hueverianieto.domain.model.SimpleListModel
import com.example.hueverianieto.ui.components.HNSimpleListAdapter
import com.example.hueverianieto.ui.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    private lateinit var binding: FragmentSettingsBinding
    private var dataList : List<SimpleListModel?> = listOf()
    //private val settingsViewModel : SettingsViewModel by viewModels()
    private lateinit var currentUser: InternalUserData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(false)
        this.binding = FragmentSettingsBinding.inflate(
            inflater, container, false
        )
        currentUser = (activity as MainActivity).currentUserData
        return this.binding.root
    }

    override fun configureUI() {
        dataList = listOf(
            SimpleListModel(
                title = "Cambiar contraseña",
                onClickListener = { }
            ),
            SimpleListModel(
                title = "Cambiar idioma",
                onClickListener = { }
            )
        )
        this.binding.settingOptionsListView.adapter =
            HNSimpleListAdapter(requireContext(), dataList)
        this.binding.settingOptionsListView.isClickable = true

    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }

}
