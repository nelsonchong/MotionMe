package com.example.motionme.ui.movieList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.motionme.R
import com.example.motionme.databinding.CellMovieListBinding

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    var data: List<Model> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, R.layout.cell_movie_list)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    data class Model(
        val imdbId: String,
        val title: String,
        val year: String,
        val poster: String
    )

    class ViewHolder(private val binding: CellMovieListBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup, layout: Int): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate<CellMovieListBinding>(inflater, layout, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(model: Model) {
            binding.tvTitle.text = model.title
            binding.tvYear.text = model.year
        }
    }
}