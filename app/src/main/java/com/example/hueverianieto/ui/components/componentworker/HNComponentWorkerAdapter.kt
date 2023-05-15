package com.example.hueverianieto.ui.components.componentworker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.model.componentworkers.ComponentWorkersModel

class HNComponentWorkerAdapter (
    private val workersModelList: List<ComponentWorkersModel>
) : RecyclerView.Adapter<HNComponentWorkerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HNComponentWorkerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HNComponentWorkerViewHolder(layoutInflater.inflate(R.layout.component_worker, parent, false))
    }

    override fun getItemCount(): Int = this.workersModelList.size

    override fun onBindViewHolder(holder: HNComponentWorkerViewHolder, position: Int) {
        holder.render(workersModelList[position])
    }

}
