package com.example.ad_project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ad_project.data.Activity
import com.example.ad_project.databinding.ItemActivityBinding

class ChannelActivityAdapter (
    // 点击事件回调：点击活动项时跳转至活动详情
    private val onActivityClick: (Activity) -> Unit
) : ListAdapter<Activity, ChannelActivityAdapter.ActivityViewHolder>(ActivityDiffCallback()) {

    /**
     * 视图持有者：管理单个活动项的视图
     */
    inner class ActivityViewHolder(private val binding: ItemActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // 绑定点击事件：点击整个活动项时触发回调
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val activity = getItem(position)
                    onActivityClick(activity)
                }
            }
        }

        fun bind(activity: Activity) {
            binding.apply {
                // 绑定数据到布局（与 item_activity.xml 中的变量对应）
                this.activity = activity
                // 立即刷新绑定，避免数据更新延迟
                executePendingBindings()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        // 初始化 ViewBinding（与 item_activity.xml 对应）
        val binding = ItemActivityBinding.inflate(inflater, parent, false)
        return ActivityViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    class ActivityDiffCallback : DiffUtil.ItemCallback<Activity>() {
        // 判断两个项是否为同一个对象（通常用唯一 ID 比较）
        override fun areItemsTheSame(oldItem: Activity, newItem: Activity): Boolean {
            return oldItem.id == newItem.id
        }

        // 判断两个项的内容是否相同（用于刷新内容变化的项）
        override fun areContentsTheSame(oldItem: Activity, newItem: Activity): Boolean {
            return oldItem == newItem
        }
    }
}