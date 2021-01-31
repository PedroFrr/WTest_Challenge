package com.pedrofr.wtest.featureui.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pedrofr.wtest.R
import com.pedrofr.wtest.data.network.featureresponse.CommentResponse
import com.pedrofr.wtest.databinding.ListItemCommentBinding
import com.pedrofr.wtest.util.loadCircleImage
import com.pedrofr.wtest.util.timestampToDate

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

/**
 * PaginatedAdapter for list of comments inside ArticleDetail
 * On first item a header is set for the RecyclerView
 */
class ArticleCommentsPaginatedAdapter :
    PagingDataAdapter<CommentResponse, RecyclerView.ViewHolder>(
        ArticleCommentsPaginatedListListDiffCallBack()
    ) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                getItem(position)?.let { holder.bind(it) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")

        }
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_TYPE_ITEM
    }


    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(
                    view
                )
            }
        }
    }

    class ViewHolder private constructor(
        private val binding: ListItemCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCommentBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }

        fun bind(item: CommentResponse) {
            with(binding) {
                commentAuthorAvatar.loadCircleImage(
                    item.avatar,
                    R.drawable.ic_baseline_supervised_user_circle_24
                )
                commentName.text = item.name
                commentPublishedAt.text = item.publishedAt.timestampToDate()
                commentBody.text = item.body
            }
        }

    }
}

private class ArticleCommentsPaginatedListListDiffCallBack :
    DiffUtil.ItemCallback<CommentResponse>() {
    override fun areContentsTheSame(oldItem: CommentResponse, newItem: CommentResponse): Boolean =
        oldItem.id == newItem.id

    override fun areItemsTheSame(oldItem: CommentResponse, newItem: CommentResponse): Boolean =
        oldItem == newItem
}