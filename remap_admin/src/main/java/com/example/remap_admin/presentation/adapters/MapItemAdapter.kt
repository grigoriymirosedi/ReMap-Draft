package com.example.remap_admin.presentation.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remap_admin.R
import com.example.remap_admin.domain.MapItem
import org.w3c.dom.Text

class MapItemAdapter(private val recyclerViewInterface: RecyclerViewInterface): RecyclerView.Adapter<MapItemAdapter.MapItemViewHolder>() {

    var mapList = mutableListOf<MapItem>()
        set(value) {
            field = value
            notifyDataSetChanged() //Нужно поменять потом будет
        }

    var onMapItemClickListener: ((MapItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.map_item, parent, false)
        return MapItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MapItemViewHolder, position: Int) {
        val mapItem = mapList[position]
        viewHolder.mapItemTitle.text = mapItem.property_name
        viewHolder.mapStreeName.text = mapItem.property_adress
        viewHolder.view.setOnClickListener {
            recyclerViewInterface.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return mapList.size
    }

    class MapItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val mapItemTitle = view.findViewById<TextView>(R.id.map_title_text)
        val mapStreeName = view.findViewById<TextView>(R.id.map_street_name)
    }
}