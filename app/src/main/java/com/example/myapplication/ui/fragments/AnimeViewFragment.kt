package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.FragmentAnimeViewBinding
import com.example.myapplication.ui.AnimeActivity
import com.example.myapplication.ui.viewmodels.AnimeViewModel
import com.example.myapplication.util.Resource


class AnimeViewFragment : Fragment() {

    private lateinit var viewModel: AnimeViewModel
    private val args:AnimeViewFragmentArgs by navArgs()
    private lateinit var fragmentBinding:FragmentAnimeViewBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var webView:WebView
    private lateinit var ivPoster:ImageView



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as? AnimeActivity)?.viewModel
            ?: throw IllegalStateException("Activity is not NewsActivity")
        val anime = args.anime

        viewModel.getAnimeDetail(anime.mal_id)

        viewModel.animeDetail.observe(viewLifecycleOwner) { response ->
            when (response) {

                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { animeResponse ->
                        val genre = animeResponse.data.genres.joinToString("/") { it.name }
                        ivPoster.visibility = View.GONE


                        if (animeResponse.data.trailer.url != null) {

                            webView.webViewClient = WebViewClient()
                            webView.settings.javaScriptEnabled = true
                            animeResponse.data.trailer.url.let { webView.loadUrl(it) }
                        } else {
                            Glide.with(this).load(animeResponse.data.images!!.jpg!!.image_url)
                                .into(fragmentBinding.ivPoster)
                            fragmentBinding.webView.visibility = View.GONE
                            fragmentBinding.ivPoster.visibility = View.VISIBLE

                        }

                        fragmentBinding.tvTitleValue.text = animeResponse.data.title
                        fragmentBinding.tvPlotValue.text = animeResponse.data.synopsis
                        fragmentBinding.tvGenreValue.text = genre
                        fragmentBinding.tvMcValue.text = "NA"
                        fragmentBinding.tvTotalEpiValue.text =
                            animeResponse.data.episodes.toString()
                        fragmentBinding.tvRatingValue.text = animeResponse.data.rating


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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentBinding = FragmentAnimeViewBinding.inflate(inflater,container,false)
        progressBar = fragmentBinding.paginationProgressBar
        webView = fragmentBinding.webView
        ivPoster = fragmentBinding.ivPoster
        return fragmentBinding.root
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }



}