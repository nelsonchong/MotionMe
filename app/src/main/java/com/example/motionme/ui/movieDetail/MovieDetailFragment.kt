package com.example.motionme.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motionme.R
import com.example.motionme.databinding.FragmentMovieDetailBinding
import com.example.motionme.extension.gone
import com.example.motionme.extension.observe
import com.example.motionme.extension.show
import com.example.motionme.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast
import timber.log.Timber

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<MovieDetailViewModel>() {

    companion object {
        const val ARG_IMDB_ID = "ARG_IMDB_ID"
    }

    override fun getLayoutResId(): Int = R.layout.fragment_movie_detail
    override val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var adapter: MovieDetailAdapter
    private var imdbId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        binding.lifecycleOwner = this

        arguments?.apply {
            imdbId = getString(ARG_IMDB_ID) ?: ""
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObservers()
        loadData()
    }

    override fun onDestroy() {
        removeObservers()
        super.onDestroy()
    }

    private fun setupView() {
        adapter = MovieDetailAdapter(requireContext()) {
            toast(it)
            Timber.tag("###").d(it)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MovieDetailFragment.adapter
        }

        binding.btnBack.onClick {
            findNavController().popBackStack()
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            observe(loading) {
                if (it) {
                    binding.progressBar.show()
                    binding.recyclerView.gone()
                } else {
                    binding.progressBar.gone()
                    binding.recyclerView.show()
                }
            }

            observe(data) {
                adapter.data = it
            }
        }
    }

    private fun loadData() {
        viewModel.load(imdbId)
    }

    private fun removeObservers() {
        viewModel.apply {
            loading.removeObservers(this@MovieDetailFragment)
            data.removeObservers(this@MovieDetailFragment)
        }
    }

}