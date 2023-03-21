package com.example.testandroid.ui.topRated

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testandroid.R
import com.example.testandroid.data.entities.MovieEntity
import com.example.testandroid.data.model.ResourceStatus
import com.example.testandroid.databinding.FragmentPopularBinding
import com.example.testandroid.databinding.FragmentTopratingBinding
import com.example.testandroid.ui.popular.PopularFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TopRatingFragment : Fragment(), TopRatingMovieItemAdapter.OnMovieClickListener {

    private var _binding: FragmentTopratingBinding? = null

    private val binding get() = _binding!!

    private val viewModel: TopRatingViewModel by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    private lateinit var topratingMovieItemAdapter: TopRatingMovieItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTopratingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMovies.layoutManager = LinearLayoutManager(context)

        viewModel.fetchTopRatingMovies.observe(viewLifecycleOwner, Observer {
            when (it.resourceStatus) {
                ResourceStatus.LOADING -> {
                    Log.e("fetchTopRatingMovies", "Loading")
                }
                ResourceStatus.SUCCESS  -> {
                    Log.e("fetchTopRatingMovies", "Success")
                    topratingMovieItemAdapter = TopRatingMovieItemAdapter(it.data!!, this@TopRatingFragment)
                    binding.rvMovies.adapter = topratingMovieItemAdapter
                }
                ResourceStatus.ERROR -> {
                    Log.e("fetchTopRatingMovies", "Failure: ${it.message} ")
                    Toast.makeText(requireContext(), "Failure: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMovieClick(movieEntity: MovieEntity) {
        val action = TopRatingFragmentDirections.actionTopRatingFragmentToDetailFragment(movieEntity)
        findNavController().navigate(action)

    }
}