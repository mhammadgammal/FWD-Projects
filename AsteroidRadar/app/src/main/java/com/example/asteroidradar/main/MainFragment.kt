package com.example.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.asteroidradar.R
import com.example.asteroidradar.databinding.FragmentMainBinding


@RequiresApi(Build.VERSION_CODES.O)
class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private val adapter = AsteroidsAdapter(AsteroidsAdapter.AsteroidClickListener { asteroid ->
        viewModel.navToDetailFragment(asteroid)
    })
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )
        binding.lifecycleOwner = this
        val activity = requireNotNull(this.activity)
        viewModel =
            ViewModelProvider(this, MainViewModelFactory(activity.application))[MainViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.navToDetailFragment.observe(viewLifecycleOwner){asteroids ->
            findNavController().navigate(
                MainFragmentDirections.navToDetailFragemnt(asteroids)
            )
        }
        binding.asteroidRecycler.adapter = adapter

        viewModel.todayAsteroids.observe(viewLifecycleOwner){ asteroids ->
            adapter.submitList(asteroids)
        }
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.view_week_asteroids -> viewModel.weekAsteroid.observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
            R.id.view_today_asteroids -> viewModel.todayAsteroids.observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
            R.id.view_saved_asteroids -> viewModel.allAsteroid.observe(viewLifecycleOwner){
                adapter.submitList(it)
            }

        }
        return true
    }
}
