package com.example.shoestore.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.shoestore.R
import com.example.shoestore.databinding.FragmentInstructionsBinding

class InstructionsFragment : Fragment() {
    private lateinit var binding: FragmentInstructionsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_instructions, container, false)
        binding.nextIBTN.setOnClickListener { view ->
            val navToShoeList = InstructionsFragmentDirections.actionInstructionsFragmentToShoeListFragment()
            view.findNavController().navigate(navToShoeList)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}