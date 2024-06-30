package com.example.asteroidradar.main

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.asteroidradar.R
import com.example.asteroidradar.databinding.FragmentMainBinding

@Suppress("DEPRECATION")
@RequiresApi(Build.VERSION_CODES.O)
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireActivity().application
        ViewModelProvider(
            this,
            MainViewModelFactory(activity, isConnected())
        )[MainViewModel::class.java]
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }

    private lateinit var adapter: AsteroidsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = AsteroidsAdapter(AsteroidsAdapter.AsteroidClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailFragment(it)
            )
        })
        viewModel.todayAsteroids.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.asteroidRecycler.adapter = adapter
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.main_overflow_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.view_week_asteroids -> {
                            adapter.submitList(viewModel.weekAsteroid.value)
                            true
                        }

                        R.id.view_saved_asteroids -> {
                            adapter.submitList(viewModel.allAsteroid.value)
                            true
                        }

                        R.id.view_today_asteroids -> {
                            adapter.submitList(viewModel.todayAsteroids.value)
                            true
                        }

                        else -> true
                    }
                }
            }
        )
    }
}