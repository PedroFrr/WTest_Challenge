package com.pedrofr.wtest.ui.postcodes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pedrofr.wtest.data.db.entities.DbPostcode
import com.pedrofr.wtest.databinding.ListItemPostcodeBinding


class PostCodeListAdapter :
    PagingDataAdapter<DbPostcode, PostCodeListAdapter.ViewHolder>(
        PostcodeListListDiffCallBack()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    class ViewHolder private constructor(
        private val binding: ListItemPostcodeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPostcodeBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }

        fun bind(item: DbPostcode) {
            with(binding) {
                postcode.text = "${item.postcodeNumber}-${item.postcodeExtension}"
                postalDesignation.text = item.postalDesignation
            }

        }

    }
}

private class PostcodeListListDiffCallBack : DiffUtil.ItemCallback<DbPostcode>() {
    override fun areContentsTheSame(oldItem: DbPostcode, newItem: DbPostcode): Boolean =
        oldItem.id == newItem.id

    override fun areItemsTheSame(oldItem: DbPostcode, newItem: DbPostcode): Boolean =
        oldItem == newItem
}