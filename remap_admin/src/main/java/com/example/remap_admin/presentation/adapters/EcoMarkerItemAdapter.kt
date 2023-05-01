package com.example.remap_admin.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remap_admin.EcoMarkerItem
import com.example.remap_admin.R
import com.squareup.picasso.Picasso

class EcoMarkerItemAdapter(private val recyclerViewInterface: RecyclerViewInterface): RecyclerView.Adapter<EcoMarkerItemAdapter.EcoMarkerItemViewHolder>() {

    var ecoMarkerList = mutableListOf<EcoMarkerItem>()
        set(value) {
            field = value
            notifyDataSetChanged() //В будущем поменять!!!
        }

    var onArticleItemClickListener: ((EcoMarkerItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EcoMarkerItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ecomarker_item, parent, false)
        return EcoMarkerItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: EcoMarkerItemViewHolder, position: Int) {
        val ecoMarkerItem = ecoMarkerList[position]
        viewHolder.ecoMarkerItemTitle.text = ecoMarkerItem.ecoTitle
        Picasso.get().load(ecoMarkerItem.imageIconURL).resize(0, 300).into(viewHolder.ecoMarkerIconImage)
        viewHolder.view.setOnClickListener {
            recyclerViewInterface.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return ecoMarkerList.size
    }

    class EcoMarkerItemViewHolder(val view: View): RecyclerView.ViewHolder(view)  {
        val ecoMarkerItemTitle = view.findViewById<TextView>(R.id.ecomarker_title_text)
        var ecoMarkerIconImage = view.findViewById<ImageView>(R.id.ecomarker_image)
    }

}