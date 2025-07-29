package com.example.ad_project.data

data class User(
    val userId: Int,
    val name: String,
    val email: String,
    val passwordHash: String,
    val role: String,
    val status: String,
    val registeredActivities: List<Activity> = emptyList(),
    val channels: List<Channel> = emptyList(),
    val receivedMessages: List<SystemMessage> = emptyList(),
    val profile: UserProfile? = null
)

