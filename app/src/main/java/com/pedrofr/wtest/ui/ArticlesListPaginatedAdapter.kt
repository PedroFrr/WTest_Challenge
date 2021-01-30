package com.pedrofr.wtest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.data.network.response.ArticleResponse
import com.pedrofr.wtest.databinding.ListItemArticleBinding


class ArticlesListPaginatedAdapter:
    PagingDataAdapter<ArticleResponse, ArticlesListPaginatedAdapter.ViewHolder>(ArticlesPaginatedListListDiffCallBack()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(
        private val binding: ListItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemArticleBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: ArticleResponse) {
            with(binding) {
                articleTitle.text = item.title
                articleAuthor.text = item.author
                articleSummary.text = item.summary

            }
        }

    }
}

private class ArticlesPaginatedListListDiffCallBack : DiffUtil.ItemCallback<ArticleResponse>() {
    override fun areContentsTheSame(oldItem: ArticleResponse, newItem: ArticleResponse): Boolean =
        oldItem.id == newItem.id

    override fun areItemsTheSame(oldItem: ArticleResponse, newItem: ArticleResponse): Boolean =
        oldItem == newItem
}