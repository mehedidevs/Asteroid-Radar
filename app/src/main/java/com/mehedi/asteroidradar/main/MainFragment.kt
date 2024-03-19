package com.mehedi.asteroidradar.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mehedi.asteroidradar.AsteroidFilter
import com.mehedi.asteroidradar.R
import com.mehedi.asteroidradar.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var asteroidAdapter: AsteroidAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        asteroidAdapter = AsteroidAdapter { asteroid ->
            findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))

        }
        binding.asteroidRecycler.adapter = asteroidAdapter

        val application = requireNotNull(this.activity).application
        val viewModelFactory = MainViewModel.Factory(application)
        val viewModel: MainViewModel by viewModels { viewModelFactory }


        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setAsteroidObserver(viewModel, AsteroidFilter.SHOW_WEEK)
        setTopMenu(viewModel)

        return binding.root
    }

    private fun setAsteroidObserver(viewModel: MainViewModel, filter: AsteroidFilter) {

        viewModel.getAsteroid(filter).observe(viewLifecycleOwner) {

            asteroidAdapter.submitList(it)


        }


    }

    private fun setTopMenu(viewModel: MainViewModel) {
        activity?.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_overflow_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_today_asteroids -> {
                        setAsteroidObserver(viewModel, AsteroidFilter.SHOW_TODAY)
                        Toast.makeText(activity, "Showing Toady!", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.menu_next_week_asteroids -> {
                        setAsteroidObserver(viewModel, AsteroidFilter.SHOW_WEEK)
                        Toast.makeText(activity, "Showing 7 Days!", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.menu_saved_asteroids -> {
                        setAsteroidObserver(viewModel, AsteroidFilter.SHOW_SAVED)
                        Toast.makeText(activity, "Showing All Saved!", Toast.LENGTH_SHORT).show()
                        true
                    }


                    else -> false
                }
            }
        }, viewLifecycleOwner)

    }


}
