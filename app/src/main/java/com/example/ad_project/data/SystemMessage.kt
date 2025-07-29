package com.example.ad_project.data

data class SystemMessage(
    val id: Int,
    val receiverId: Int,
    val receiver: User?,         // 可为空，避免循环引用
    val title: String,
    val content: String,
    val isRead: Boolean,
    val sentAt: String           // ISO 8601 格式，例如 "2025-07-28T10:38:25.375"
)
