package com.example.remap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.remap.models.EcoMarkers
import com.squareup.picasso.Picasso

class EcoMarkersAdapter(private val ecoMarkersList: ArrayList<EcoMarkers>, private val recyclerViewInterface: RecyclerViewInterface): RecyclerView.Adapter<EcoMarkersAdapter.EcoMarkersViewHolder>() {
    var recycleViewInterface: RecyclerViewInterface = recyclerViewInterface

    class EcoMarkersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val eco_marker_image: ImageView = itemView.findViewById(R.id.eco_marker_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EcoMarkersViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.eco_marker_item, parent, false)
        return EcoMarkersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EcoMarkersViewHolder, position: Int) {
        val currentItem = ecoMarkersList[position]
        //holder.eco_marker_image.setImageResource(currentItem.imageResource)

        Picasso.get().load(currentItem.imageIconURL).resize(250,0).into(holder.eco_marker_image)

        holder.itemView.setOnClickListener{
            recycleViewInterface.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return ecoMarkersList.size
    }
}