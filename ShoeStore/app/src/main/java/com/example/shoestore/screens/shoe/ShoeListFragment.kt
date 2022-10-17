package com.example.shoestore.screens.shoe

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.shoestore.R
import com.example.shoestore.databinding.FragmentShoeListBinding
import com.example.shoestore.databinding.ItemCardBinding
import com.example.shoestore.model.Shoe

class ShoeListFragment : Fragment() {

    private lateinit var shoeListBinding: FragmentShoeListBinding
    private lateinit var cardBinding: ItemCardBinding
    private lateinit var shoeViewModel: ShoeListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        shoeListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_list, container, false)
        shoeViewModel = ViewModelProvider(requireActivity())[ShoeListViewModel::class.java]
        shoeViewModel.shoeList.observe(viewLifecycleOwner) { newShoe ->
            newShoe.forEach { shoe ->
                addCard(shoe)
            }
        }
        shoeListBinding.addShoeFAB.setOnClickListener {
            findNavController().navigate(
                ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment()
            )
        }
        // Inflate the layout for this fragment
        return shoeListBinding.root
    }

    private fun addCard(shoe: Shoe?) {
        val view: View = View.inflate(context, R.layout.item_card, null)
        cardBinding = DataBindingUtil.bind(view)!!
        cardBinding.shoe = shoe
        shoeListBinding.mainLayout.addView(cardBinding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return item.onNavDestinationSelected(findNavController()) ||
                super.onOptionsItemSelected(item)
    }
}