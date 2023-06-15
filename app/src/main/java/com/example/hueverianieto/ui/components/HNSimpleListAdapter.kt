package com.example.hueverianieto.ui.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.hueverianieto.R
import com.example.hueverianieto.databinding.ComponentItemSettingsBinding
import com.example.hueverianieto.domain.model.SimpleListModel

class HNSimpleListAdapter (
    context: Context, dataList: List<SimpleListModel?>
) : ArrayAdapter<SimpleListModel>(context, R.layout.component_item_settings, dataList) {

    var binding : ComponentItemSettingsBinding = ComponentItemSettingsBinding.inflate(
        LayoutInflater.from(context)
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view = convertView
        val item = getItem(position)

        if (view == null) {
            view = LayoutInflater.from(context).inflate(
                R.layout.component_item_settings, parent, false)
        }

        val titleView = view!!.findViewById<TextView>(R.id.title_item_list)
        val containerView = view.findViewById<LinearLayout>(R.id.container)
        titleView.text = item?.title
        containerView.setOnClickListener(item?.onClickListener)

        return view

    }

}
