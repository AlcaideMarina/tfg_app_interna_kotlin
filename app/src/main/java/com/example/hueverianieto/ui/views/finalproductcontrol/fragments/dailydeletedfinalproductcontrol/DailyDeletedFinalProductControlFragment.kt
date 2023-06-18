package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.dailydeletedfinalproductcontrol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentDailyFinalProductControlBinding
import com.example.hueverianieto.domain.model.finalproductcontrol.FPCDailyContainerItemModel
import com.example.hueverianieto.domain.model.finalproductcontrol.MonthlyFPCContainerModel
import com.example.hueverianieto.ui.components.componentdailyfinalproductcontrol.ComponentDailyFinalProductControlAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyDeletedFinalProductControlFragment : BaseFragment() {

    private lateinit var binding: FragmentDailyFinalProductControlBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var monthlyDeletedFPCContainerModel: MonthlyFPCContainerModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        val args: DailyDeletedFinalProductControlFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.monthlyDeletedFPCContainerModel = args.monthlyDeletedFPCContainerModel

        this.binding = FragmentDailyFinalProductControlBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        hideButtons()
        initRecyclerView()
    }

    override fun setObservers() {
        // Not necessary
    }

    override fun setListeners() {
        // Not necessary
    }

    override fun updateUI(state: BaseState) {
        // Not necessary
    }

    private fun hideButtons() {
        this.binding.deletedFpcButton.visibility = View.GONE
        this.binding.addButton.visibility = View.GONE
    }

    private fun initRecyclerView() {
        val list = mutableListOf<FPCDailyContainerItemModel>()
        for (item in monthlyDeletedFPCContainerModel.fpcDataList) {
            val fpcDailyContainerItemModel = FPCDailyContainerItemModel(
                item,
            ) {
                // TODO Navegación
            }
            list.add(fpcDailyContainerItemModel)

        }
        if (list.isEmpty()) {
            this.binding.containerWaringNoDailyFpc.visibility = View.VISIBLE
            this.binding.dailyFpcRecyclerView.visibility = View.GONE
            this.binding.containerWaringNoDailyFpc.setTitle("No hay registros")
            this.binding.containerWaringNoDailyFpc.setText("No hay registros de producto final eliminados para el mes seleccionado en la base de datos.")
        } else {
            this.binding.dailyFpcRecyclerView.layoutManager = LinearLayoutManager(this.context)
            this.binding.dailyFpcRecyclerView.adapter =
                ComponentDailyFinalProductControlAdapter(list)
            this.binding.dailyFpcRecyclerView.visibility = View.VISIBLE
            this.binding.containerWaringNoDailyFpc.visibility = View.GONE
        }
    }

}
