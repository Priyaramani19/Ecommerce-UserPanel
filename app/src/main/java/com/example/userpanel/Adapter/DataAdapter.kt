package com.example.userpanel.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userpanel.Fragments.CategoryFragment
import com.example.userpanel.ModelClass.ModelClassRead
import com.example.userpanel.R
import com.example.userpanel.Screen.DataActivity

class DataAdapter(val fragment: FragmentActivity?, val list: ArrayList<ModelClassRead>) :
    RecyclerView.Adapter<DataAdapter.ViewData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view = LayoutInflater.from(fragment).inflate(R.layout.data_item, parent, false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.productName.text = list[position].name
        holder.productPrice.text = list[position].price

        Glide.with(fragment!!).load(list[position].uri).into(holder.productImage)

        holder.viewContainer.setOnClickListener {
            var intent = Intent(fragment,DataActivity::class.java)
            intent.putExtra("img",list[position].uri)
            intent.putExtra("name",list[position].name)
            intent.putExtra("price",list[position].price)
            intent.putExtra("offer",list[position].offer)
            intent.putExtra("description",list[position].desc)
            intent.putExtra("category",list[position].category)
            intent.putExtra("catId",list[position].catId)
            intent.putExtra("key",list[position].key)
            fragment.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productName = itemView.findViewById<TextView>(R.id.productName)!!
        var productPrice = itemView.findViewById<TextView>(R.id.productPrice)!!
        var productImage = itemView.findViewById<ImageView>(R.id.productImage)!!
        var viewContainer = itemView.findViewById<CardView>(R.id.viewContainer)!!
    }

}