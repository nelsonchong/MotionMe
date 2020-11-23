package com.example.motionme.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.motionme.R
import com.example.motionme.databinding.FragmentMovieListBinding
import com.example.motionme.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.appcompat.v7.coroutines.onQueryTextListener

@AndroidEntryPoint
class MovieListFragment : BaseFragment<MovieListViewModel>() {

    override fun getLayoutResId(): Int = R.layout.fragment_movie_list
    override val viewModel: MovieListViewModel by viewModels()
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var adapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.searchView.onQueryTextListener {
            onQueryTextChange {
                true
            }

            onQueryTextSubmit {
                viewModel.search(it ?: "")
                true
            }
        }

        adapter = MovieListAdapter()

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@MovieListFragment.adapter
        }
    }

    private fun setupObservers() {

    }

}