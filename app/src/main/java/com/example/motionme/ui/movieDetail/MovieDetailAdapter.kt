package com.example.motionme.ui.movieDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.motionme.R
import com.example.motionme.databinding.*
import com.example.motionme.extension.hide
import com.example.motionme.extension.show
import org.jetbrains.anko.sdk27.coroutines.onClick

class MovieDetailAdapter(
    private val context: Context,
    private val onTap: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class Type {
        Poster {
            override val viewType: Int = 0
        },
        Title {
            override val viewType: Int = 1
        },
        Header {
            override val viewType: Int = 2
        },
        Body {
            override val viewType: Int = 3
        },
        Cast {
            override val viewType: Int = 4
        },
        SmallSpace {
            override val viewType: Int = 5
        },
        LargeSpace {
            override val viewType: Int = 6
        };

        abstract val viewType: Int
    }

    var data: List<Model> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Type.Poster.viewType -> {
                val binding = DataBindingUtil.inflate<CellMovieDetailPosterBinding>(
                    inflater, R.layout.cell_movie_detail_poster, parent, false
                )
                PosterViewHolder(binding)
            }
            Type.Title.viewType -> {
                val binding = DataBindingUtil.inflate<CellMovieDetailTitleBinding>(
                    inflater, R.layout.cell_movie_detail_title, parent, false
                )
                TitleViewHolder(binding) {
                    onTap(it)
                }
            }
            Type.Header.viewType -> {
                val binding = DataBindingUtil.inflate<CellMovieDetailHeaderBinding>(
                    inflater, R.layout.cell_movie_detail_header, parent, false
                )
                HeaderViewHolder(binding)
            }
            Type.Body.viewType -> {
                val binding = DataBindingUtil.inflate<CellMovieDetailBodyBinding>(
                    inflater, R.layout.cell_movie_detail_body, parent, false
                )
                BodyViewHolder(binding)
            }
            Type.Cast.viewType -> {
                val binding = DataBindingUtil.inflate<CellMovieDetailCastBinding>(
                    inflater, R.layout.cell_movie_detail_cast, parent, false
                )
                CastViewHolder(binding)
            }
            Type.SmallSpace.viewType -> {
                SmallSpaceViewHolder(
                    inflater.inflate(
                        R.layout.cell_movie_detail_small_space,
                        parent,
                        false
                    )
                )
            }
            else -> {
                LargeSpaceViewHolder(
                    inflater.inflate(
                        R.layout.cell_movie_detail_large_space,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PosterViewHolder -> holder.bind(context, data[position] as PosterModel)
            is TitleViewHolder -> holder.bind(data[position] as TitleModel)
            is HeaderViewHolder -> holder.bind(data[position] as HeaderModel)
            is BodyViewHolder -> holder.bind(data[position] as BodyModel)
            is CastViewHolder -> holder.bind(data[position] as CastModel)
            is SmallSpaceViewHolder -> holder.bind()
            is LargeSpaceViewHolder -> holder.bind()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type.viewType
    }

    class PosterViewHolder(
        private val binding: CellMovieDetailPosterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, model: PosterModel) {
            binding.ivPoster.clipToOutline = true

            Glide
                .with(context)
                .load(model.poster)
                .centerCrop()
                .placeholder(R.color.colorGrey)
                .into(binding.ivPoster)
        }
    }

    class TitleViewHolder(
        private val binding: CellMovieDetailTitleBinding,
        private val onTap: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: TitleModel) {
            binding.tvTitle.text = model.title
            binding.tvInfo.text = model.info

            binding.btnPlay.onClick {
                onTap(model.imdbId)
            }
        }
    }

    class HeaderViewHolder(
        private val binding: CellMovieDetailHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: HeaderModel) {
            binding.textView.text = model.text
        }
    }

    class BodyViewHolder(
        private val binding: CellMovieDetailBodyBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: BodyModel) {
            binding.textView.text = model.text
        }
    }

    class CastViewHolder(
        private val binding: CellMovieDetailCastBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CastModel) {
            if (model.data.size > 3) {
                binding.castView4.show()
                binding.castView4.name = model.data[3].name
                binding.castView4.role = model.data[3].role
            } else {
                binding.castView4.hide()
            }

            if (model.data.size > 2) {
                binding.castView3.show()
                binding.castView3.name = model.data[2].name
                binding.castView3.role = model.data[2].role
            } else {
                binding.castView3.hide()
            }

            if (model.data.size > 1) {
                binding.castView2.show()
                binding.castView2.name = model.data[1].name
                binding.castView2.role = model.data[1].role
            } else {
                binding.castView2.hide()
            }

            if (model.data.isNotEmpty()) {
                binding.castView1.show()
                binding.castView1.name = model.data[0].name
                binding.castView1.role = model.data[0].role
            } else {
                binding.castView1.hide()
            }
        }
    }

    class SmallSpaceViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        fun bind() {}
    }

    class LargeSpaceViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        fun bind() {}
    }

    interface Model {
        val type: Type
    }

    data class PosterModel(
        val poster: String,
        override val type: Type = Type.Poster
    ) : Model

    data class TitleModel(
        val title: String,
        val info: String,
        val imdbId: String,
        override val type: Type = Type.Title
    ) : Model

    data class HeaderModel(
        val text: String,
        override val type: Type = Type.Header
    ) : Model

    data class BodyModel(
        val text: String,
        override val type: Type = Type.Body
    ) : Model

    data class CastModel(
        val data: ArrayList<CastInfo>,
        override val type: Type = Type.Cast
    ) : Model

    data class SmallSpaceModel(
        override val type: Type = Type.SmallSpace
    ) : Model

    data class LargeSpaceModel(
        override val type: Type = Type.LargeSpace
    ) : Model

    data class CastInfo(
        val name: String,
        val role: String
    )
}