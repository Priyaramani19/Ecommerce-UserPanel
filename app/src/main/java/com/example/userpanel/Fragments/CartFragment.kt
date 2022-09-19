package com.example.userpanel.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userpanel.Adapter.CartAdapter
import com.example.userpanel.ModelClass.ModelClassCart
import com.example.userpanel.databinding.FragmentCartBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    private var total: Int = 0
    private var amount: Int = 0
    private var i = 0
    var list = ArrayList<ModelClassCart>()
    var amountList = ArrayList<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)

        readData()

        return binding.root
    }

    fun setTotal() {

        total = 0
        i = 0
        while (i < amountList.size) {
            total += amountList[i]
            i++
        }

        binding.pPrice.text = total.toString()
        binding.tPrice.text = total.toString()

    }

    fun readData() {

        list.clear()

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var firebaseReference = firebaseDatabase.reference

        firebaseReference.child("Cart").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                amountList.clear()

                for (x in snapshot.children) {

                    var productName = x.child("name").value.toString()
                    var productDesc = x.child("desc").value.toString()
                    var productPrice = x.child("price").value.toString()
                    var productUri = x.child("uri").value.toString()
                    var productOffer = x.child("offer").value.toString()
                    var productCat = x.child("category").value.toString()
                    var productCatId = x.child("catId").value.toString()
                    var productQty = x.child("qty").value.toString()
                    var key = x.key.toString()

                    amount = productPrice.toInt() * productQty.toInt()
                    amountList.add(amount)

                    var m1 = ModelClassCart(
                        productName,
                        productDesc,
                        productPrice,
                        productOffer,
                        productUri,
                        productCat,
                        productCatId,
                        productQty.toInt(),
                        key
                    )
                    list.add(m1)

                }
                Log.e("TAG", "onDataChange: $amountList")

                setTotal()

                setupRV(list)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun setupRV(list: ArrayList<ModelClassCart>) {
        var adapter = CartAdapter(activity, list)
        var layoutManager = LinearLayoutManager(activity)
        binding.rvView.adapter = adapter
        binding.rvView.layoutManager = layoutManager
    }

}