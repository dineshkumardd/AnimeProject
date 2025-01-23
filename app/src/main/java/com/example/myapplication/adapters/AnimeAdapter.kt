package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemAnimePreviewBinding
import com.example.myapplication.models.Data

class AnimeAdapter : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>(){

    inner class AnimeViewHolder(val binding:ItemAnimePreviewBinding):RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.mal_id == newItem.mal_id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimeAdapter.AnimeViewHolder {
        val itemBinding = ItemAnimePreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AnimeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AnimeAdapter.AnimeViewHolder, position: Int) {
        val anime = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(anime.images!!.jpg!!.image_url).into(holder.binding.ivPoster)
            holder.binding.tvTitle.text = anime.title
            holder.binding.tvNoOfEpi.text = anime.episodes.toString()
            holder.binding.tvRating.text = anime.rating

            setOnClickListener {
                onItemClickListener?.let {
                    it(anime)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener:((Data)-> Unit)?=null

    fun setOnItemClickListener(listener:(Data)->Unit){
        onItemClickListener = listener
    }
}