package com.example.ad_project.data

data class ChannelMessage(
    val id: Int,
    val channelId: Int,
    val channel: Channel?,         // 可为空，避免循环引用
    val postedById: Int,
    val postedBy: User?,           // 可为空
    val title: String,
    val content: String,
    val postedAt: String,          // ISO 8601 格式，例如 "2025-07-28T10:38:25.375"
    val isPinned: Boolean,
    val isVisible: Boolean
)
