package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.util.Util
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemArticleBinding
import com.example.newsapp.model.Article


class SavedNewsAdapter : RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>() {

        private val diffUtilCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == oldItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

        }

    inner class SavedNewsViewHolder(var view: ItemArticleBinding) : RecyclerView.ViewHolder(view.root)

    val differ = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemArticleBinding>(
            inflater,
            R.layout.item_saved_news,
            parent,
            false
        )

        return SavedNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {

        val article = differ.currentList[position]
        holder.view.article = article

        // item click listener
        // bind these click listener later
        holder.itemView.setOnClickListener {

            onItemClickListener?.let {
                article.let { article ->
                    it(article)

                }
            }
        }

        holder.view.ivShare.setOnClickListener {
            onShareNewsClick?.let {
                article.let { it1 ->
                    it(it1)

                }
            }
        }
    }

        override fun getItemCount() = differ.currentList.size

        private var onItemClickListener: ((Article) -> Util)? = null
        private var onShareNewsClick: ((Article) -> Util)? = null


        fun setOnItemClickListener(listener: (Article) -> Unit) {
            onItemClickListener = listener
        }

        fun onShareClickListener(listener: (Article) -> Unit) {
            onShareNewsClick = listener
        }

}