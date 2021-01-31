package com.pedrofr.wtest.featureui.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.databinding.ListItemArticleBinding
import com.pedrofr.wtest.ui.articles.ArticleListFragmentDirections


class ArticlesListPaginatedAdapter:
    PagingDataAdapter<DbArticle, ArticlesListPaginatedAdapter.ViewHolder>(ArticlesPaginatedListListDiffCallBack()) {

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

        fun bind(item: DbArticle) {
            with(binding) {
                articleTitle.text = item.title
                articleAuthor.text = item.author
                articleSummary.text = item.summary
                articleCard.setOnClickListener {
                    val direction = ArticleListFragmentDirections.articleListToDetail(item.id)
                    Navigation.findNavController(it).navigate(direction)
                }

            }
        }

    }
}

private class ArticlesPaginatedListListDiffCallBack : DiffUtil.ItemCallback<DbArticle>() {
    override fun areContentsTheSame(oldItem: DbArticle, newItem: DbArticle): Boolean =
        oldItem.id == newItem.id

    override fun areItemsTheSame(oldItem: DbArticle, newItem: DbArticle): Boolean =
        oldItem == newItem
}