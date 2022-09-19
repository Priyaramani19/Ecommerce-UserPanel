package com.example.userpanel.Fragments

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userpanel.Adapter.CategoryAdapter
import com.example.userpanel.Adapter.DataAdapter
import com.example.userpanel.ModelClass.ModelClassRead
import com.example.userpanel.R
import com.example.userpanel.Screen.MainActivity
import com.example.userpanel.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    var list = ArrayList<ModelClassRead>()
    var carouselList = mutableListOf<CarouselItem>()

    var catImage = arrayOf(
        "https://assetscdn1.paytm.com/images/catalog/product/A/AP/APPYASH-GALLERYAASH1296504D3AAF65/10.jpg",
        "https://ninecolours.s3.amazonaws.com/image/cache/products-2018/March-2021/Chiffon-Western-Wear-Dress-In-Peach-Colour-DR1046754-A-1200x1799.jpg",
        "https://images.pexels.com/photos/1620760/pexels-photo-1620760.jpeg?auto=compress&cs=tinysrgb&w=1600",
        "https://images.unsplash.com/photo-1623975522547-3d7ad176973e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OXx8bWVucyUyMGZhc2hpb258ZW58MHx8MHx8&w=1000&q=80",
        "https://saree.b-cdn.net/media/catalog/product/p/s/psadh3181a-light-blue-georgette-saree-with-weaving.jpg",
        "https://5.imimg.com/data5/SELLER/Default/2021/6/LJ/SJ/UK/130085995/bridal-lehenga-1000x1000.jpg",
        "https://wp.missmalini.com/wp-content/uploads/2019/09/BeFunky-collage-27-4.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT0ZN1GH5OQNlcNk1bkz7mE-V33Q_QRAUJNAQ&usqp=CAU",
        "https://res.cloudinary.com/purnesh/image/upload/w_540,f_auto,q_auto:eco,c_limit/171615445675406.jpg",
        "https://www.mockupdaddy.com/wp-content/uploads/2021/10/iPhone-13-and-Macbook-Pro-Mockup.jpg",
        "https://cdn.shopify.com/s/files/1/2186/5207/products/1_f7aa0fb3-5819-4f5d-9ecc-4c348023ae99_600x.jpg?v=1655495620",
        "https://cdn.shopify.com/s/files/1/0558/3801/5637/products/81p5f3Krm6L._SL1500.jpg?v=1659615321",
        "https://cdn.vox-cdn.com/thumbor/lD7Krs0uHXTTvMH8TGvkL-oSyFA=/0x0:5515x3665/1200x800/filters:focal(2317x1392:3199x2274)/cdn.vox-cdn.com/uploads/chorus_image/image/70894045/shutterstock_1042252666.0.jpg",
        "https://marlayzbruder.com/wp-content/uploads/2020/09/universal-stretch-couch-covers-waterproof-sofa-seat-cover-sofa-seat-covers-l-bebd8a34f2f07d92.jpg",
        "https://cdn.lifehack.org/wp-content/uploads/2016/10/28033136/Body-Care-Products.jpg"
    )

    var catTxt = arrayOf(
        "Kurtis & Suits wear",
        "Western wear",
        "Kids",
        "Men wear",
        "Saree",
        "Lehenghas",
        "Accessories",
        "Jewellery",
        "Beauty",
        "Electronics",
        "Foot wear",
        "Home decor",
        "Kitchen Products",
        "HOme Textiles",
        "Health Care"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        readData()
        setImageSlider()
        setupCatRV()
        categoryFragment()

        return binding.root
    }

    private fun categoryFragment() {
        binding.catImage.setImageResource(R.drawable.cat_rv)

        binding.cardImage.setOnClickListener {

            parentFragmentManager.beginTransaction().apply {
                replace(R.id.framLayout, CategoryFragment())
                commit()
            }


        }
    }

    private fun setupCatRV() {

        var adapter = CategoryAdapter(activity, catImage, catTxt)
        var layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.catRvView.layoutManager = layoutManager
        binding.catRvView.adapter = adapter

    }

    private fun setImageSlider() {

        carouselList.add(CarouselItem(imageUrl = "https://dubaidrivingcenter.net/images/offers/DDC-offers.jpg"))
        carouselList.add(CarouselItem(imageUrl = "https://i.pinimg.com/originals/8f/c4/6f/8fc46f8c1038d0723013866cdd01f26b.jpg"))
        carouselList.add(CarouselItem(imageUrl = "https://static.vecteezy.com/system/resources/previews/000/258/463/original/vector-cash-back-colorful-sale-banner-design.jpg"))
        carouselList.add(CarouselItem(imageUrl = "https://cdn.grabon.in/gograbon/images/web-images/uploads/1617875488697/clothing-offers.jpg"))
        carouselList.add(CarouselItem(imageUrl = "https://www.shopickr.com/wp-content/uploads/2015/10/amazon-india-electronics-sale-2015-banner1.jpg"))
        carouselList.add(CarouselItem(imageUrl = "https://images-eu.ssl-images-amazon.com/images/G/31/img18/Audio/Feb/FAECF3B9_1.PNG"))
        carouselList.add(CarouselItem(imageUrl = "https://img.freepik.com/premium-vector/fast-delivery-by-scooter-mobile-e-commerce-concept-online-food-pizza-order-packaging-box-infographic_131114-3.jpg?w=2000"))

        binding.imageSlider.setData(carouselList)

    }

    private fun readData() {

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
                    list.add(m1)

                }
                setupRV(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setupRV(list: ArrayList<ModelClassRead>) {
        var adapter = DataAdapter(activity, list)
        var layoutManager = GridLayoutManager(activity, 2)
        binding.rvView.adapter = adapter
        binding.rvView.layoutManager = layoutManager
    }

}