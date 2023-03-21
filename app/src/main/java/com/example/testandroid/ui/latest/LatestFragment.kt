package com.example.testandroid.ui.latest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
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
import com.example.testandroid.databinding.FragmentLatestBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LatestFragment : Fragment(), LatestMovieItemAdapter.OnMovieClickListener {

    private var _binding: FragmentLatestBinding? = null

    private val binding get() = _binding!!

    private val viewModel: LatestViewModel by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    private lateinit var LatestMovieItemAdapter: LatestMovieItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLatestBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMovies.layoutManager = LinearLayoutManager(context)

        viewModel.fetchLatestMovies.observe(viewLifecycleOwner, Observer {
            when (it.resourceStatus) {
                ResourceStatus.LOADING -> {
                    Log.e("fetchLatestMovies", "Loading")
                }
                ResourceStatus.SUCCESS  -> {
                    Log.e("fetchLatestMovies", "Success")
                    LatestMovieItemAdapter = LatestMovieItemAdapter(it.data!!, this@LatestFragment)
                    binding.rvMovies.adapter =  LatestMovieItemAdapter
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
        val action =  LatestFragmentDirections.actionLatestFragmentToDetailFragment(movieEntity)
        findNavController().navigate(action)

    }
}