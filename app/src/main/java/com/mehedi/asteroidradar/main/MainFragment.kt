package com.mehedi.asteroidradar.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

        setObserver(viewModel)

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setObserver(viewModel: MainViewModel) {

        viewModel.getSteroid().observe(viewLifecycleOwner) {

            asteroidAdapter.submitList(it)


        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
