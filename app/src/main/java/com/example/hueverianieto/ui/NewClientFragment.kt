package com.example.hueverianieto.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.components.titletextinputlayout.TitleTextInputLayoutAdapter
import com.example.hueverianieto.data.components.TextInputLayoutModel
import com.example.hueverianieto.data.components.TitleTextInputLayoutModel
import com.example.hueverianieto.databinding.FragmentNewClientBinding
import com.example.hueverianieto.utils.ClientUtils

class NewClientFragment : BaseFragment() {

    private lateinit var binding: FragmentNewClientBinding

    private var fieldList: List<TitleTextInputLayoutModel> = listOf()

    override fun injection() {
        // TODO: Sin implementar
    }

    override fun configureUI() {

        fieldList = ClientUtils.newClientFields()
        initRecyclerView()
    }

    override fun setObservers() {
        //
    }

    override fun setListeners() {
        //
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AllClientsActivity).configNav("Nuevo cliente")
        this.binding = FragmentNewClientBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    private fun initRecyclerView() {
        this.binding.newUserFieldsRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.newUserFieldsRecyclerView.adapter = TitleTextInputLayoutAdapter(fieldList)
    }
}