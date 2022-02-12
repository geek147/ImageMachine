package com.envious.imagemachine.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.envious.domain.model.Machine
import com.envious.imagemachine.R
import com.envious.imagemachine.databinding.ListRowMachineBinding
import com.envious.imagemachine.ui.fragments.DetailFragment

class MachineAdapter(private var fragment: Fragment) : RecyclerView.Adapter<MachineAdapter.MainViewHolder>() {
    private var listItem: MutableList<Machine> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(fragment.context).inflate(R.layout.list_row_machine, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return if (listItem.isNullOrEmpty()) {
            0
        } else {
            listItem.size
        }
    }

    override fun getItemId(position: Int): Long {
        val item: Machine = listItem[position]
        return item.id.toLong()
    }

    fun addData(list: List<Machine>) {
        this.listItem.addAll(list)
        notifyDataSetChanged()
    }

    fun setData(list: List<Machine>) {
        this.listItem.clear()
        this.listItem.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        with(holder.binding) {
            textName.text = listItem[position].name
            textType.text = listItem[position].type.name
            textQr.text = listItem[position].qrNumber.toString()
            textLastUpdate.text = listItem[position].lastUpdate
            val bundle = Bundle()
            bundle.putInt(DetailFragment.EXTRA_ID, listItem[position].id)
            holder.itemView.setOnClickListener {
                fragment.findNavController().navigate(
                    R.id.action_mainFragment_to_detailFragment,
                    bundle
                )
            }
        }
    }

    class MainViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding = ListRowMachineBinding.bind(view)
    }
}
