package com.example.ad_project.data

data class UserProfile(
    val id: Int,
    val userId: Int,
    val user: User?,               // 可为空，避免循环引用
    val tags: List<Tag> = emptyList(),
    val age: Int?,                 // 可为空
    val height: Double?,           // 单位：cm，可为空
    val weight: Double?,           // 单位：kg，可为空
    val gender: String?            // 可选值：male / female / other
)
