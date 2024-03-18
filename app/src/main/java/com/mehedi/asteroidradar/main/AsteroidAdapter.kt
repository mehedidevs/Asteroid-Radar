package com.mehedi.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mehedi.asteroidradar.Asteroid
import com.mehedi.asteroidradar.databinding.ItemAsteroidBinding


class AsteroidAdapter(private val asteroidListener: AsteroidListener) :
    ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>(COMPARATOR) {


    class AsteroidViewHolder(private val binding: ItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid, asteroidListener: AsteroidListener) {
            binding.asteroid = asteroid

            binding.root.setOnClickListener {
                asteroidListener.onClickAsteroid(asteroid)
            }

        }

        companion object {
            fun from(parent: ViewGroup) =
                AsteroidViewHolder(
                    ItemAsteroidBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
        }


    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Asteroid>() {
            override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
                return oldItem == newItem
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {

        return AsteroidViewHolder.from(parent)

    }


    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        getItem(position)?.let { asteroid ->
            holder.bind(asteroid, asteroidListener)
        }


    }


    fun interface AsteroidListener {

        fun onClickAsteroid(asteroid: Asteroid)

    }

}