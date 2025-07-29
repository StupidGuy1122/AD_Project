package com.example.ad_project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ad_project.data.ChannelMessage
import com.example.ad_project.databinding.ItemMessageBinding

class MessageAdapter : ListAdapter<ChannelMessage, MessageAdapter.MessageViewHolder>(MessageDiffCallback()) {
    inner class MessageViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChannelMessage) {
            binding.apply {
                // 绑定数据到布局（与 item_message.xml 中的变量对应）
                this.message = message
                // 立即刷新绑定，确保数据实时更新
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        // 初始化 ViewBinding（与 item_message.xml 对应）
        val binding = ItemMessageBinding.inflate(inflater, parent, false)
        return MessageViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    class MessageDiffCallback : DiffUtil.ItemCallback<ChannelMessage>() {
        // 判断两个消息项是否为同一个对象（通过消息 ID 区分）
        override fun areItemsTheSame(oldItem: ChannelMessage, newItem: ChannelMessage): Boolean {
            return oldItem.id == newItem.id
        }

        // 判断两个消息项的内容是否相同（内容、发送者、时间等变化时触发刷新）
        override fun areContentsTheSame(oldItem: ChannelMessage, newItem: ChannelMessage): Boolean {
            return oldItem.content == newItem.content &&
                    oldItem.postedById == newItem.postedById &&
                    oldItem.postedAt == newItem.postedAt
        }
    }
}