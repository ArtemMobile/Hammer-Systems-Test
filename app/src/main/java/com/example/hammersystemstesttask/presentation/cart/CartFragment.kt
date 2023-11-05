package com.example.hammersystemstesttask.presentation.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hammersystemstesttask.R
import com.example.hammersystemstesttask.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private val binding by lazy {
        FragmentCartBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

}