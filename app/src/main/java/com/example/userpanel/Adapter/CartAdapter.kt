package com.example.userpanel.Adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userpanel.Adapter.CartAdapter.ViewData
import com.example.userpanel.Fragments.CartFragment
import com.example.userpanel.ModelClass.ModelClassCart
import com.example.userpanel.ModelClass.ModelClassUpCart
import com.example.userpanel.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.FirebaseDatabase

class CartAdapter(
    private val cartFragment: FragmentActivity?, private val list: ArrayList<ModelClassCart>
) :
    RecyclerView.Adapter<ViewData>() {

    var i = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        val view = LayoutInflater.from(cartFragment).inflate(R.layout.cart_item, parent, false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {

        holder.productName.text = list[position].name
        holder.productPrice.text = list[position].price
        holder.productDesc.text = list[position].desc
        holder.pOffer.text = list[position].offer
        holder.productQty.text = list[position].qty.toString()

        Glide.with(cartFragment!!).load(list[position].uri).into(holder.productImage)

        holder.nextImg.setOnClickListener {
            cartDialog(position, holder)
        }

        holder.remove.setOnClickListener {
            deleteDialog(position)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productName = itemView.findViewById<TextView>(R.id.productName)!!
        var productDesc = itemView.findViewById<TextView>(R.id.productDesc)!!
        var productPrice = itemView.findViewById<TextView>(R.id.productPrice)!!
        var productImage = itemView.findViewById<ImageView>(R.id.productImage)!!
        var nextImg = itemView.findViewById<ImageView>(R.id.nextImg)!!
        var remove = itemView.findViewById<RelativeLayout>(R.id.remove)!!
        var pOffer = itemView.findViewById<TextView>(R.id.pOffer)!!
        var productQty = itemView.findViewById<TextView>(R.id.productQty)!!
    }

    private fun deleteDialog(position: Int) {

        val dialog = Dialog(cartFragment!!)
        dialog.show()
        dialog.setContentView(R.layout.delete_dialog_item)
        dialog.setCancelable(false)

        val closeBtn = dialog.findViewById<TextView>(R.id.closeBtn)
        val yesBtn = dialog.findViewById<TextView>(R.id.yesBtn)

        closeBtn?.setOnClickListener {
            dialog.dismiss()
        }

        yesBtn?.setOnClickListener {
            val firebaseDatabase = FirebaseDatabase.getInstance()
            val firebaseReference = firebaseDatabase.reference

            firebaseReference.child("Cart").child(list[position].key).removeValue()
            list.clear()
            dialog.dismiss()
        }

    }

    private fun cartDialog(position: Int, holder: ViewData) {

        i = list[position].qty

        val dialog = BottomSheetDialog(cartFragment!!)
        dialog.setContentView(R.layout.cart_dialog_item)

        val closeBtn = dialog.findViewById<ImageView>(R.id.closeBtn)
        val plusBtn = dialog.findViewById<RelativeLayout>(R.id.plusBtn)
        val minusBtn = dialog.findViewById<RelativeLayout>(R.id.minusBtn)
        val qtyNumber = dialog.findViewById<TextView>(R.id.qtyNumber)
        val pImg = dialog.findViewById<ImageView>(R.id.pImg)
        val pName = dialog.findViewById<TextView>(R.id.pName)
        val pPrice = dialog.findViewById<TextView>(R.id.pPrice)
        val pOffer = dialog.findViewById<TextView>(R.id.pOffer)
        val continueBtn = dialog.findViewById<Button>(R.id.continueBtn)

        Glide.with(cartFragment!!.applicationContext).load(list[position].uri).into(pImg!!)

        pName?.text = list[position].name
        pPrice?.text = list[position].price
        pOffer?.text = list[position].offer
        qtyNumber?.text = i.toString()

        continueBtn?.setOnClickListener {

            val m1 = ModelClassUpCart(
                list[position].name,
                list[position].desc,
                list[position].price,
                list[position].offer,
                list[position].uri,
                list[position].category,
                list[position].catId,
                qtyNumber?.text.toString()
            )

            val firebaseDatabase = FirebaseDatabase.getInstance()
            val firebaseReference = firebaseDatabase.reference

            firebaseReference.child("Cart").child(list[position].key).setValue(m1)

            Toast.makeText(cartFragment!!, "Update Product Successfully!!", Toast.LENGTH_SHORT)
                .show()

            list.clear()

            holder.productQty.text = i.toString()
            dialog.dismiss()
        }

        plusBtn?.setOnClickListener {
            if (i < 10) {
                i++
                qtyNumber?.text = i.toString()
            }
        }

        minusBtn?.setOnClickListener {
            if (i > 1) {
                i--
                qtyNumber?.text = i.toString()
            }
        }

        closeBtn?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }
}
