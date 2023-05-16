package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.dailyfinalproductcontrol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
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
    private var deletedList = mutableListOf<FPCData>()
    private val dailyFinalProductControlViewModel: DailyFinalProductControlViewModel by viewModels()

    private var lastLot = (0).toLong()

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
        this.binding.deletedFpcButton.setText("Eliminados")
        deletedList = mutableListOf<FPCData>()
        initRecyclerView()
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        this.binding.deletedFpcButton.setOnClickListener {
            this.dailyFinalProductControlViewModel.navigateToDailyDeletedFPC(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData,
                    "monthlyDeletedFPCContainerModel" to
                            MonthlyFPCContainerModel(
                                initDate = monthlyFPCContainerModel.initDate,
                                endDate = monthlyFPCContainerModel.endDate,
                                fpcDataList = deletedList
                            )
                )
            )
        }
        this.binding.addButton.setOnClickListener {
            this.dailyFinalProductControlViewModel.navigateToNewFPC(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData,
                    "lastLot" to lastLot
                )
            )
        }
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
                    this.dailyFinalProductControlViewModel.navigateToFPCDetail(
                        this.view,
                        bundleOf(
                            "currentUserData" to currentUserData,
                            "fpcData" to item
                        )
                    )
                }
                list.add(fpcDailyContainerItemModel)
                if (item.lot > lastLot) lastLot = item.lot
            } else {
                deletedList.add(item)
            }
        }
        if (list.isEmpty()) {
            this.binding.containerWaringNoDailyFpc.visibility = View.VISIBLE
            this.binding.containerWaringNoDailyFpc.setTitle("No hay entradas")
            this.binding.containerWaringNoDailyFpc.setText("Mo hay información sobre Control de Producto Final, con entradas sin eliminar.")
        } else {
            this.binding.dailyFpcRecyclerView.layoutManager = LinearLayoutManager(this.context)
            this.binding.dailyFpcRecyclerView.adapter = ComponentDailyFinalProductControlAdapter(list)
            this.binding.dailyFpcRecyclerView.visibility = View.VISIBLE
        }
    }

}
