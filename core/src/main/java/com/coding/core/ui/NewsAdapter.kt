package com.coding.core.ui

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.coding.core.R
import com.coding.core.data.source.local.entity.NewsEntity
import com.coding.core.databinding.ItemListNewsBinding
import com.coding.core.utils.DateFormatter
import java.util.TimeZone

class NewsAdapter(private val onItemClick: (NewsEntity) -> Unit) : ListAdapter<NewsEntity, NewsAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onItemClick)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    class MyViewHolder(private val binding: ItemListNewsBinding, val onItemClick: (NewsEntity) -> Unit) : RecyclerView.ViewHolder(
        binding.root
    ) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(news: NewsEntity) {
            binding.newsTitle.text = news.title
            binding.newsDate.text = DateFormatter.formatDate(news.publishedAt, TimeZone.getDefault().id)
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error)
                )
                .into(binding.newsImage)
            itemView.setOnClickListener {
                onItemClick(news)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsEntity> =
            object : DiffUtil.ItemCallback<NewsEntity>() {
                override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem.title == newItem.title
                }
            }
    }
}
