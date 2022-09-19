package com.example.userpanel.Adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userpanel.ModelClass.ModelClassCategoryRead
import com.example.userpanel.R

class CatAdapter(
    val activity: FragmentActivity?,
    val list: ArrayList<ModelClassCategoryRead>,
    val catImage: Array<String>
) :
    RecyclerView.Adapter<CatAdapter.ViewData>() {

    var colorList = arrayListOf<Int>(
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.cat_item, parent, false)
        return ViewData(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewData, position: Int) {

        Glide.with(activity!!).load(catImage[position]).into(holder.catImage)
        holder.catTxt.text = list[position].catName
        holder.sideLine.isVisible = true

        holder.container.setBackgroundColor(activity!!.getColor(colorList[position]))

        var i = 0
        holder.container.setOnClickListener {

//            categoryID = list1[position].id

            colorList[position] = R.color.white

//            categoryScreen.ReadDataAllPRoduct(categoryID)

            holder.container.setBackgroundColor(activity!!.getColor(colorList[position]))
            while (i < list.size) {
                if (i != position) {
                    colorList[i] = R.color.grey
                }
                i++
            }
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sideLine = itemView.findViewById<RelativeLayout>(R.id.sideLine)
        var catImage = itemView.findViewById<ImageView>(R.id.catImage)
        var catTxt = itemView.findViewById<TextView>(R.id.catTxt)
        var container = itemView.findViewById<RelativeLayout>(R.id.container)
    }

}
