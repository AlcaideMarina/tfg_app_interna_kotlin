package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.dailyfinalproductcontrol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.FPCData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentDailyFinalProductControlBinding
import com.example.hueverianieto.domain.model.finalproductcontrol.FPCDailyContainerItemModel
import com.example.hueverianieto.domain.model.finalproductcontrol.MonthlyFPCContainerModel
import com.example.hueverianieto.ui.components.componentdailyfinalproductcontrol.ComponentDailyFinalProductControlAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyFinalProductControlFragment : BaseFragment() {

    private lateinit var binding: FragmentDailyFinalProductControlBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var monthlyFPCContainerModel: MonthlyFPCContainerModel
    private val deletedList = mutableListOf<FPCData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        val args: DailyFinalProductControlFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.monthlyFPCContainerModel = args.monthlyFPCContainerModel

        this.binding = FragmentDailyFinalProductControlBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        initRecyclerView()
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

    private fun initRecyclerView() {
        val list = mutableListOf<FPCDailyContainerItemModel>()
        for (item in monthlyFPCContainerModel.fpcDataList) {
            if (!item.deleted) {
                val fpcDailyContainerItemModel = FPCDailyContainerItemModel(
                    item
                ) {
                    // TODO Navegación
                }
                list.add(fpcDailyContainerItemModel)
            } else {
                deletedList.add(item)
            }
        }
        if (list.isEmpty()) {
            // TODO
        } else {
            this.binding.dailyFpcRecyclerView.layoutManager = LinearLayoutManager(this.context)
            this.binding.dailyFpcRecyclerView.adapter = ComponentDailyFinalProductControlAdapter(list)
            this.binding.dailyFpcRecyclerView.visibility = View.VISIBLE
        }
    }

}
