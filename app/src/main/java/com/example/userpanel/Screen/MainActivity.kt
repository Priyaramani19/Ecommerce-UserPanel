package com.example.userpanel.Screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.userpanel.Fragments.*
import com.example.userpanel.R
import com.example.userpanel.databinding.ActivityMainBinding
import com.github.kwasow.bottomnavigationcircles.BottomNavigationCircles
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.tenclouds.fluidbottomnavigation.FluidBottomNavigationItem
import com.tenclouds.fluidbottomnavigation.listener.OnTabSelectedListener

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(HomeFragment())
        setMenuBN()
        windowColor()
        navigationClick()
    }

    private fun navigationClick() {

        binding.bottomNavigation.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                Log.e("TAG", "onNavigationItemSelected: ${item.itemId}" )
                when(item.itemId){
                    R.id.home -> loadFragment(HomeFragment())
                    R.id.category -> loadFragment(CategoryFragment())
                    R.id.search -> loadFragment(SearchFragment())
                    R.id.cart -> loadFragment(CartFragment())
                    R.id.account -> loadFragment(AccountFragment())
                }
                return true
            }
        })


//        binding.bottomNavigation.onTabSelectedListener = object : OnTabSelectedListener {
//            override fun onTabSelected(position: Int) {
//                when (position) {
//                    0 -> loadFragment(HomeFragment())
//                    1 -> loadFragment(CategoryFragment())
//                    2 -> loadFragment(SearchFragment())
//                    3 -> loadFragment(CartFragment())
//                    4 -> loadFragment(AccountFragment())
//                }
//            }
//
//        }
    }

    private fun windowColor() {
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.statusBar)
    }

    private fun setMenuBN() {
//        binding.bottomNavigation.items = listOf(
//            FluidBottomNavigationItem(
//                getString(R.string.home),
//                ContextCompat.getDrawable(this, R.drawable.home)
//            ),
//            FluidBottomNavigationItem(
//                getString(R.string.category),
//                ContextCompat.getDrawable(this, R.drawable.category)
//            ),
//            FluidBottomNavigationItem(
//                getString(R.string.search),
//                ContextCompat.getDrawable(this, R.drawable.search)
//            ),
//            FluidBottomNavigationItem(
//                getString(R.string.cart),
//                ContextCompat.getDrawable(this, R.drawable.cart)
//            ),
//            FluidBottomNavigationItem(
//                getString(R.string.account),
//                ContextCompat.getDrawable(this, R.drawable.account)
//            )
//        )
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.framLayout, fragment)
            commit()
        }
    }

}