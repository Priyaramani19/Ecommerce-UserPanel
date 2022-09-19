package com.example.userpanel.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userpanel.Adapter.CatAdapter
import com.example.userpanel.Adapter.itemAdapter
import com.example.userpanel.ModelClass.ModelClassCategoryRead
import com.example.userpanel.ModelClass.ModelClassRead
import com.example.userpanel.databinding.FragmentCategoryBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CategoryFragment : Fragment() {

    var catList = ArrayList<ModelClassCategoryRead>()
    var itemList = ArrayList<ModelClassRead>()
    private var catImage = arrayOf(
        "https://cdn-icons-png.flaticon.com/512/4172/4172657.png",
        "https://cdn-icons-png.flaticon.com/512/80/80614.png",
        "https://cdn-icons-png.flaticon.com/128/1962/1962745.png",
        "https://cdn-icons-png.flaticon.com/128/88/88772.png",
        "https://cdn-icons-png.flaticon.com/512/6614/6614588.png",
        "https://cdn-icons-png.flaticon.com/512/4932/4932296.png",
        "https://cdn-icons-png.flaticon.com/512/5684/5684563.png",
        "https://cdn-icons-png.flaticon.com/128/2956/2956145.png",
        "https://cdn-icons-png.flaticon.com/128/100/100304.png",
        "https://cdn-icons-png.flaticon.com/512/6356/6356437.png",
        "https://cdn-icons-png.flaticon.com/128/7138/7138816.png",
        "https://cdn-icons-png.flaticon.com/128/3531/3531744.png",
        "https://cdn-icons-png.flaticon.com/128/5996/5996731.png",
        "https://cdn-icons-png.flaticon.com/128/3081/3081993.png",
        "https://cdn-icons-png.flaticon.com/128/941/941486.png",
        "https://cdn-icons-png.flaticon.com/512/99/99952.png",
        "https://cdn-icons-png.flaticon.com/128/3731/3731709.png",
        "https://cdn-icons-png.flaticon.com/128/2789/2789622.png",
        "https://cdn-icons-png.flaticon.com/128/2797/2797957.png",
        "https://cdn-icons-png.flaticon.com/128/5532/5532356.png",
        "https://cdn-icons-png.flaticon.com/128/862/862032.png",
        "https://cdn-icons-png.flaticon.com/128/3109/3109884.png",
        "https://cdn-icons-png.flaticon.com/128/5122/5122754.png",
        "https://cdn-icons-png.flaticon.com/128/2629/2629781.png",
        "https://cdn-icons-png.flaticon.com/128/62/62887.png",
        "https://cdn-icons-png.flaticon.com/128/24/24697.png",
        "https://cdn-icons-png.flaticon.com/128/1706/1706281.png",
        "https://cdn-icons-png.flaticon.com/128/527/527989.png",
        "https://cdn-icons-png.flaticon.com/128/2739/2739899.png",
        "https://cdn-icons-png.flaticon.com/128/5529/5529622.png",
        "https://cdn-icons-png.flaticon.com/128/838/838907.png",
        "https://cdn-icons-png.flaticon.com/128/1695/1695521.png",
        "https://cdn-icons-png.flaticon.com/128/2521/2521144.png",
        "https://cdn-icons-png.flaticon.com/128/763/763331.png"
    )

    lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)

        getCategory()
        readData()

        return binding.root
    }


    private fun getCategory() {

        catList.clear()

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var firebaseReference = firebaseDatabase.reference

        firebaseReference.child("Category").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (x in snapshot.children) {
                    var catId = x.child("catId").value.toString()
                    var catName = x.child("catName").value.toString()
                    var key = x.key.toString()

                    var m1 = ModelClassCategoryRead(catId, catName, key)
                    catList.add(m1)
                }
                catSetupRV(catList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun catSetupRV(list: ArrayList<ModelClassCategoryRead>) {

        var adapter = CatAdapter(activity, list, catImage)
        var layoutManager = LinearLayoutManager(activity)
        binding.catRvView.adapter = adapter
        binding.catRvView.layoutManager = layoutManager

    }


    private fun readData() {

        itemList.clear()

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var fireBaseReference = firebaseDatabase.reference
        fireBaseReference.child("Product").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (x in snapshot.children) {

                    var productName = x.child("name").value.toString()
                    var productDesc = x.child("desc").value.toString()
                    var productPrice = x.child("price").value.toString()
                    var productUri = x.child("uri").value.toString()
                    var productOffer = x.child("offer").value.toString()
                    var productCat = x.child("category").value.toString()
                    var productCatId = x.child("catId").value.toString()
                    var key = x.key.toString()

                    var m1 = ModelClassRead(
                        productName,
                        productDesc,
                        productPrice,
                        productOffer,
                        productUri,
                        productCat,
                        productCatId,
                        key
                    )
                    itemList.add(m1)

                }
                itemSetupRV(itemList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
            }
        })

    }


    private fun itemSetupRV(itemList: ArrayList<ModelClassRead>) {

        var adapter = itemAdapter(activity, itemList)
        var layoutManager = LinearLayoutManager(activity)
        binding.itemRvView.adapter = adapter
        binding.itemRvView.layoutManager = layoutManager

    }


}