package com.example.userpanel.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userpanel.ModelClass.ModelClassRead
import com.example.userpanel.R

class itemAdapter(val activity: FragmentActivity?, val itemList: ArrayList<ModelClassRead>) :
    RecyclerView.Adapter<itemAdapter.ViewData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        Glide.with(activity!!).load(itemList[position].uri).into(holder.productImg)
        holder.pnameTxt.text = itemList[position].name
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productImg = itemView.findViewById<ImageView>(R.id.productImg)
        var pnameTxt = itemView.findViewById<TextView>(R.id.pnameTxt)
    }

}