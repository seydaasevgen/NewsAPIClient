package com.seyda.newsapiclient.presentation.adapter

import android.telephony.AccessNetworkConstants.UtranBand
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seyda.newsapiclient.data.model.Article
import com.seyda.newsapiclient.databinding.NewsListItemBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            //to decide whether two objects represent the same item in the old and new list
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            //to decide whether two objects have the same data
            return oldItem == newItem
        }
    }

    //now we need to get the AsyncListDiffer
    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    inner class NewsViewHolder(
        val binding: NewsListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.tvTitle.text = article.title
            binding.tvDescription.text = article.description
            binding.tvPublishedAt.text = article.publishedAt
            binding.tvSource.text = article.source?.name

            Glide.with(binding.ivArticleImage.context)
                .load(article.urlToImage)
                .into(binding.ivArticleImage)

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }

    private var onItemClickListener : ((Article)->Unit)?=null

    fun setOnItemClickListener(listener :(Article)->Unit){
        onItemClickListener = listener
    }
}