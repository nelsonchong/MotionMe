package com.example.motionme.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.motionme.R
import com.example.motionme.databinding.FragmentMovieListBinding
import com.example.motionme.extension.observe
import com.example.motionme.ui.base.BaseFragment
import com.example.motionme.ui.movieDetail.MovieDetailFragment
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
        setupObservers()
    }

    override fun onDestroy() {
        removeObservers()
        super.onDestroy()
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

        adapter = MovieListAdapter(requireContext()) {
            val bundle = bundleOf(
                MovieDetailFragment.ARG_IMDB_ID to it
            )
            findNavController().navigate(R.id.navMovieListToMovieDetail, bundle)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MovieListFragment.adapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    (recyclerView.layoutManager as LinearLayoutManager?)?.let {
                        if (viewModel.isLastItemShown(it.findLastCompletelyVisibleItemPosition())) {
                            viewModel.load()
                        }
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            observe(data) {
                adapter.data = it
            }
        }
    }

    private fun removeObservers() {
        viewModel.apply {
            data.removeObservers(this@MovieListFragment)
        }
    }

}