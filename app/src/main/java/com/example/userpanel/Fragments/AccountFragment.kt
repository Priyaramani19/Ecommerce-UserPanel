package com.example.userpanel.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.userpanel.R
import com.example.userpanel.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    lateinit var binding : FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater,container,false)




        return binding.root
    }

}