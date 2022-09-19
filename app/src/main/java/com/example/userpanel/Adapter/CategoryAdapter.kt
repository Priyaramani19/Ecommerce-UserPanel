package com.example.userpanel.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userpanel.R

class CategoryAdapter(
    val activity: FragmentActivity?,
    val catImage: Array<String>,
    val catTxt: Array<String>
) :
    RecyclerView.Adapter<CategoryAdapter.ViewData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {

        Glide.with(activity!!).load(catImage[position]).placeholder(R.drawable.google).into(holder.catImage)
        holder.catTxt.text = catTxt[position]

    }

    override fun getItemCount(): Int {
        return catImage.size
    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var catImage = itemView.findViewById<ImageView>(R.id.image)
        var catTxt = itemView.findViewById<TextView>(R.id.catTxt)
    }

}
