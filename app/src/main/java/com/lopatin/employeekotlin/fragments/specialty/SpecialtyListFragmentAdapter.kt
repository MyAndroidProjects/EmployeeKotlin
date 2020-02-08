package com.lopatin.employeekotlin.fragments.specialty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lopatin.employeekotlin.R
import com.lopatin.employeekotlin.model.data.Specialty
import kotlinx.android.synthetic.main.item_specialty_fragment_list.view.*

class SpecialtyListFragmentAdapter (
    private val list: ArrayList<Specialty>,
    private val clickListener: SpecialtyListItemClickListener
) :
    RecyclerView.Adapter<SpecialtyListFragmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_specialty_fragment_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.specialtyView.specialtyTitle.text = item.name
        holder.itemView.setOnClickListener {
            clickListener.onSpecialtyListItemClick(item)
        }
    }

    class ViewHolder(val specialtyView: View) : RecyclerView.ViewHolder(specialtyView)

    interface SpecialtyListItemClickListener {
        fun onSpecialtyListItemClick(specialty: Specialty)
    }
}