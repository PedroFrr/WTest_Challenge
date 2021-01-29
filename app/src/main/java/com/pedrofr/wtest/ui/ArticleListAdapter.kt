package com.pedrofr.wtest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.databinding.ListItemArticleBinding

class ArticleListAdapter(private val navigateToArticleDetail: (view: View, articleId: String) -> Unit) :
    ListAdapter<DbArticle, ArticleListAdapter.ViewHolder>(ArticleListListDiffCallBack()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { holder.bind(it, navigateToArticleDetail) }
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

        fun bind(
            item: DbArticle,
            navigateToArticleDetail: (view: View, articleId: String) -> Unit
        ) {
            with(binding) {
                articleTitle.text = item.title
                articleAuthor.text = item.author
                articleSummary.text = item.summary

                articleCard.setOnClickListener { view ->
                    navigateToArticleDetail(view, item.id)
                }
            }

        }
    }
}

private class ArticleListListDiffCallBack : DiffUtil.ItemCallback<DbArticle>() {
    override fun areContentsTheSame(oldItem: DbArticle, newItem: DbArticle): Boolean =
        oldItem.id == newItem.id

    override fun areItemsTheSame(oldItem: DbArticle, newItem: DbArticle): Boolean =
        oldItem == newItem
}