package com.example.ad_project.data

data class Channel(
    val channelId: Int,
    val name: String,
    val createdBy: Int,
    val status: String, // "active", "archived", "deleted"
    val creator: User?, // 可为空
    val description: String,
    val members: List<User> = emptyList(),
    val messages: List<ChannelMessage> = emptyList(),
    val tags: List<Tag> = emptyList()
)
