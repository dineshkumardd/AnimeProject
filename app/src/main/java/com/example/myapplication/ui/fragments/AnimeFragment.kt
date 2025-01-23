package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.AnimeAdapter
import com.example.myapplication.databinding.FragmentAnimeBinding
import com.example.myapplication.ui.AnimeActivity
import com.example.myapplication.ui.viewmodels.AnimeViewModel
import com.example.myapplication.util.Resource

class AnimeFragment : Fragment(R.layout.fragment_anime) {

    private lateinit var viewModel: AnimeViewModel
    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var recycler: RecyclerView
    private lateinit var fragmentBinding:FragmentAnimeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerview()

        viewModel = (activity as? AnimeActivity)?.viewModel ?:throw IllegalStateException("Activity is not AnimeActivity")


        animeAdapter.setOnItemClickListener {
            it.let {
                val bundle = Bundle().apply {
                    putSerializable("anime",it)
                }
                findNavController().navigate(
                    R.id.action_animeFragment_to_animeViewFragment,
                    bundle
                )
            }
        }

        viewModel.animeList.observe(viewLifecycleOwner) { response ->
            when (response) {

                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { animeResponse ->
                        animeAdapter.differ.submitList(animeResponse.data)

                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occurred :$message", Toast.LENGTH_SHORT)
                            .show()
                    }

                }

                is Resource.Loading -> {
                    showProgressBar()

                }
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = FragmentAnimeBinding.inflate(inflater,container,false)
        recycler = fragmentBinding.rvAnime
        progressBar = fragmentBinding.paginationProgressBar

        return fragmentBinding.root
    }

    private fun setUpRecyclerview() {
        animeAdapter = AnimeAdapter()
        recycler.apply {
            adapter = animeAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }
}