package com.example.ad_project.data

data class Activity(
    val activityId: Int,
    val title: String,
    val description: String,
    val location: String,
    val startTime: String, // ISO 8601 格式，例如 "2025-07-28T10:36:46.256"
    val endTime: String,
    val status: String,
    val createdBy: Int,
    val creator: User?,
    val tags: List<Tag>,
    val registeredUsers: List<User>
)
