package com.example.ad_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ad_project.R
import com.example.ad_project.data.Channel
import com.example.ad_project.databinding.ItemChannelBinding

class ChannelAdapter(
    private val onItemClick: (Channel) -> Unit, // 点击频道项（非按钮区域）的回调
    private val onJoinClick: (Channel) -> Unit  // 点击加入按钮的回调
) : ListAdapter<Channel, ChannelAdapter.ChannelViewHolder>(ChannelDiffCallback()) {

    // 记录已加入的频道ID，用于更新按钮状态
    private val joinedChannelIds = mutableSetOf<Int>()

    inner class ChannelViewHolder(private val binding: ItemChannelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // 绑定频道项点击事件（点击非按钮区域跳转详情）
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val channel = getItem(position)
                    onItemClick(channel)
                }
            }

            // 绑定加入按钮点击事件
            binding.joinButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val channel = getItem(position)
                    onJoinClick(channel)
                }
            }
        }

        fun bind(channel: Channel) {
            binding.apply {
                this.channel = channel
                // 设置按钮状态（已加入/未加入）
                val isJoined = joinedChannelIds.contains(channel.channelId)
                joinButton.text = if (isJoined) "leave" else "join"
                joinButton.isEnabled = !isJoined // 已加入则禁用按钮
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChannelBinding.inflate(inflater, parent, false)
        return ChannelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // 标记频道为已加入，更新按钮状态
    fun markAsJoined(channelId: Int) {
        joinedChannelIds.add(channelId)
        notifyItemChanged(currentList.indexOfFirst { it.channelId == channelId })
    }

    // 标记频道为未加入（可选，根据需求使用）
    fun markAsNotJoined(channelId: Int) {
        joinedChannelIds.remove(channelId)
        notifyItemChanged(currentList.indexOfFirst { it.channelId == channelId })
    }

    class ChannelDiffCallback : DiffUtil.ItemCallback<Channel>() {
        override fun areItemsTheSame(oldItem: Channel, newItem: Channel): Boolean {
            return oldItem.channelId == newItem.channelId
        }

        override fun areContentsTheSame(oldItem: Channel, newItem: Channel): Boolean {
            return oldItem == newItem
        }
    }
}

