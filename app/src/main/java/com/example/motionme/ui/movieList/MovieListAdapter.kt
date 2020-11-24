package com.example.motionme.ui.movieList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.motionme.R
import com.example.motionme.databinding.CellLoadingBinding
import com.example.motionme.databinding.CellMovieListBinding
import com.example.motionme.extension.hide
import com.example.motionme.extension.show
import org.jetbrains.anko.sdk27.coroutines.onClick

class MovieListAdapter(
    private val context: Context,
    private val onTap: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<Model> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            Type.Loading.viewType -> {
                val binding = DataBindingUtil.inflate<CellLoadingBinding>(
                    inflater, R.layout.cell_loading,
                    parent,
                    false
                )
                return LoadingViewHolder(binding)
            }

            else -> {
                val binding = DataBindingUtil.inflate<CellMovieListBinding>(
                    inflater,
                    R.layout.cell_movie_list,
                    parent,
                    false
                )
                return MovieViewHolder(binding, onTap)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> holder.bind(context, data[position] as MovieGridModel)
            is LoadingViewHolder -> holder.bind()
        }
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type.viewType
    }

    class MovieViewHolder(
        private val binding: CellMovieListBinding,
        private val onTap: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, movieGridModel: MovieGridModel) {
            binding.tvTitleLeft.text = movieGridModel.movieInfoLeft.title
            binding.tvYearLeft.text = movieGridModel.movieInfoLeft.year

            movieGridModel.movieInfoRight?.let {
                binding.cardViewRight.show()
                binding.tvTitleRight.text = it.title
                binding.tvYearRight.text = it.year
            } ?: kotlin.run {
                binding.cardViewRight.hide()
            }

            Glide
                .with(context)
                .load(movieGridModel.movieInfoLeft.poster)
                .centerCrop()
                .placeholder(R.color.colorGrey)
                .into(binding.ivPosterLeft)

            movieGridModel.movieInfoRight?.poster?.let {
                Glide
                    .with(context)
                    .load(it)
                    .centerCrop()
                    .placeholder(R.color.colorGrey)
                    .into(binding.ivPosterRight)
            }

            binding.cardViewLeft.onClick {
                onTap(movieGridModel.movieInfoLeft.imdbId)
            }

            binding.cardViewRight.onClick {
                movieGridModel.movieInfoRight?.let {
                    onTap(it.imdbId)
                }
            }
        }
    }

    class LoadingViewHolder(
        binding: CellLoadingBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind() { }
    }

    enum class Type {
        Data {
            override val viewType: Int = 0
        },
        Loading {
            override val viewType: Int = 1
        };

        abstract val viewType: Int
    }

    interface Model {
        val type: Type
    }

    data class MovieGridModel(
        var movieInfoLeft: MovieInfo,
        var movieInfoRight: MovieInfo? = null,
        override var type: Type = Type.Data
    ): Model

    data class LoadingModel(
        override var type: Type = Type.Loading
    ): Model

    data class MovieInfo(
        val imdbId: String,
        val title: String,
        val year: String,
        val poster: String
    )
}