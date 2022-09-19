package com.example.userpanel.Screen

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.userpanel.ModelClass.ModelClassCart
import com.example.userpanel.ModelClass.ModelClassRead
import com.example.userpanel.R
import com.example.userpanel.databinding.ActivityDataBinding
import com.google.firebase.database.FirebaseDatabase

class DataActivity : AppCompatActivity() {

    var key: String? = null
    var catId: String? = null
    var category: String? = null
    var imageUri: Uri? = null
    var desc: String? = null
    var offer: String? = null
    var price: String? = null
    var name: String? = null
    var uri: String? = null
    var qty = "1"
    lateinit var binding: ActivityDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uri = intent.getStringExtra("img").toString()
        name = intent.getStringExtra("name").toString()
        price = intent.getStringExtra("price").toString()
        offer = intent.getStringExtra("offer").toString()
        desc = intent.getStringExtra("description").toString()
        category = intent.getStringExtra("category").toString()
        catId = intent.getStringExtra("catId").toString()
        key = intent.getStringExtra("key").toString()

        imageUri = Uri.parse(uri)

        initClick()
        setData()
        windowColor()


    }

    private fun setData() {
        Glide.with(this).load(imageUri).into(binding.pImg)
        binding.pName.text = name
        binding.pPrice.text = price
        binding.pOffer.text = offer
        binding.pDescription.text = desc
    }

    private fun windowColor() {
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.statusBar)
    }

    private fun initClick() {
        binding.addCartBtn.setOnClickListener {
            var m1 = ModelClassCart(
                name!!,
                desc!!,
                price!!,
                offer!!,
                uri!!,
                category!!,
                catId!!,
                qty.toInt(),
                key!!
            )
            insertData(m1)
        }
    }

    private fun insertData(m1: ModelClassCart) {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var firebaseReference = firebaseDatabase.reference

        firebaseReference.child("Cart").push().setValue(m1)

        Toast.makeText(this, "Add Product Successfully!!", Toast.LENGTH_SHORT)
            .show()
    }

}