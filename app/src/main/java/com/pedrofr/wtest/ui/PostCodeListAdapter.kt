package com.pedrofr.wtest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedrofr.wtest.data.db.entities.DbPostcode
import com.pedrofr.wtest.databinding.ListItemPostcodeBinding


class PostCodeListAdapter(private val navigateToDetail: (view: View, postCodeId: Long) -> Unit) :
    ListAdapter<DbPostcode, PostCodeListAdapter.ViewHolder>(PostcodeListListDiffCallBack()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, navigateToDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(
        private val binding: ListItemPostcodeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPostcodeBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(
            item: DbPostcode,
            navigateToDetail: (view: View, cityId: Long) -> Unit
        ) {
            with(binding) {
                //TODO add bindings - clickListenter, name ....
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