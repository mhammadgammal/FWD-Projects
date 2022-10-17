package com.example.shoestore.screens.shoe

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shoestore.R
import com.example.shoestore.databinding.FragmentShoeDetailBinding

class ShoeDetailFragment : Fragment() {
    private lateinit var binding: FragmentShoeDetailBinding
    private lateinit var shoeViewModel: ShoeListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_detail,
            container,
            false
        )
        setHasOptionsMenu(true)
        binding.lifecycleOwner = this
        shoeViewModel = ViewModelProvider(requireActivity())[ShoeListViewModel::class.java]
        binding.shoeViewModel = shoeViewModel

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.done_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_done)
            if (binding.shoeSizeTF.editText?.text.toString().isEmpty())
                Toast.makeText(context, "you must provide size", Toast.LENGTH_SHORT).show()
            else {
                shoeViewModel.addNewShoe()
                findNavController().navigate(
                    ShoeDetailFragmentDirections.actionShoeDetailFragmentToShoeListFragment()
                )
            }
        return super.onOptionsItemSelected(item)
    }
}