package com.example.motionme.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.motionme.R
import com.example.motionme.databinding.FragmentMovieListBinding
import com.example.motionme.ui.base.BaseFragment

class MovieListFragment : BaseFragment<MovieListViewModel>() {

    override fun getLayoutResId(): Int = R.layout.fragment_movie_list
    override val viewModel: MovieListViewModel by viewModels()
    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

}