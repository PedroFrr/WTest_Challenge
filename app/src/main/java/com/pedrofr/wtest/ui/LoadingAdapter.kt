package com.pedrofr.wtest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedrofr.wtest.databinding.ItemLoadingStateBinding

class LoadingAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingAdapter.LoadingStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bindState(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        return LoadingStateViewHolder.from(parent)
    }

    class LoadingStateViewHolder private constructor(
        private val binding: ItemLoadingStateBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): LoadingStateViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLoadingStateBinding.inflate(layoutInflater, parent, false)
                return LoadingStateViewHolder(binding)
            }
        }

        fun bindState(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
            }

        }

    }


}