package com.example.ad_project

import com.example.ad_project.data.Activity
import com.example.ad_project.data.Tag
import com.example.ad_project.data.User
import com.example.ad_project.data.UserProfile
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


data class ChannelReportDto(
    val channelId: Int,
    val reportContent: String
)
data class UpdateUserProfileDto(
    val age: Int?,
    val height: Double?,
    val weight: Double?,
    val gender: String?,               // "male", "female", "other"
    val tagIds: List<Int> = emptyList() // 标签 ID 列表
)
data class MessageResponse(
    val message: String
)

interface ApiService {

    @GET("api/Activity/{id}")
    suspend fun getActivityById(@Path("id") id: Int): Response<Activity>

    @GET("api/Activity/activities/search")
    suspend fun searchActivities(@Query("keyword") keyword: String): Response<List<Activity>>

    @POST("api/Activity/activities/favourite/{activityId}")
    suspend fun addFavoriteActivity(@Path("activityId") activityId: Int): Response<String>

    @DELETE("api/Activity/activities/favourite/{activityId}")
    suspend fun removeFavoriteActivity(@Path("activityId") activityId: Int): Response<String>

    @GET("api/Activity/favorites")
    suspend fun getFavoriteActivities(): Response<List<Activity>>

    @GET("api/Activity/registered")
    suspend fun getPassedActivities(): Response<List<Activity>>

    @GET("api/User/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<User>

    @PUT("api/User/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: User): Response<Unit>

    @PUT("api/UserProfile/update")
    suspend fun updateProfile(@Body dto: UpdateUserProfileDto): Response<MessageResponse>

    @GET("api/UserProfile/me")
    suspend fun getMyProfile(): Response<UserProfile>

    @GET("api/Tag")
    suspend fun getAllTags(): Response<List<Tag>>

    @GET("api/Tag/{id}")
    suspend fun getTagById(@Path("id") id: Int): Response<Tag>

    @GET("api/Tag/profile/{profileId}")
    suspend fun getTagsByProfile(@Path("profileId") profileId: Int): Response<List<Tag>>

    @GET("api/Tag/activity/{activityId}")
    suspend fun getTagsByActivity(@Path("activityId") activityId: Int): Response<List<Tag>>

    @POST("api/channel/channels/join")
    suspend fun joinChannel(@Query("channelId") channelId: Int): Response<String>

    @POST("api/channel/channels/report")
    suspend fun reportChannel(@Body dto: ChannelReportDto): Response<String>

    companion object {
        private const val BASE_URL = "http://10.0.2.2:5114/"
        val instance: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
        //根据id获取活动信息
        suspend fun getActivityById(id: Int): Activity? {
            val response = ApiService.instance.getActivityById(id)
            return if (response.isSuccessful) response.body() else null
        }
        //根据关键词寻找活动信息
        suspend fun searchActivities(keyword: String): List<Activity> {
            val response = ApiService.instance.searchActivities(keyword)
            return response.body() ?: emptyList()
        }
        //收藏活动
        suspend fun addFavoriteActivity(activityId: Int): String {
            val response = instance.addFavoriteActivity(activityId)
            return response.body() ?: "收藏失败"
        }
        //取消收藏活动
        suspend fun removeFavoriteActivity(activityId: Int): String {
            val response = instance.removeFavoriteActivity(activityId)
            return response.body() ?: "取消收藏失败"
        }
        //获取用户收藏的活动
        suspend fun getFavoriteActivities(): List<Activity> {
            val response = ApiService.instance.getFavoriteActivities()
            return response.body() ?: emptyList()
        }
        //获取用户准备参加的活动
        suspend fun getPassedActivities(): List<Activity> {
            val response = ApiService.instance.getPassedActivities()
            return response.body() ?: emptyList()
        }
        //根据id获取用户信息
        suspend fun getUserById(userId: Int): User? {
            val response = ApiService.instance.getUserById(userId)
            return if (response.isSuccessful) response.body() else null
        }
        //更新用户简介
        suspend fun updateUserProfile(dto: UpdateUserProfileDto): String {
            val response = ApiService.instance.updateProfile(dto)
            return response.body()?.message ?: "更新失败"
        }
        //获取用户简介
        suspend fun getMyProfile(): UserProfile? {
            val response = ApiService.instance.getMyProfile()
            return response.body()
        }
        //获取所有tag
        suspend fun getAllTags(): List<Tag> {
            val response = ApiService.instance.getAllTags()
            return response.body() ?: emptyList()
        }
        //根据id获取tag
        suspend fun getTagById(tagId: Int): Tag? {
            val response = ApiService.instance.getTagById(tagId)
            return response.body()
        }
        //获取profile内的tag
        suspend fun getTagsByProfile(profileId: Int): List<Tag> {
            val response = ApiService.instance.getTagsByProfile(profileId)
            return response.body() ?: emptyList()
        }
        //获取活动所对应的tag
        suspend fun getTagsByActivity(activityId: Int): List<Tag> {
            val response = ApiService.instance.getTagsByActivity(activityId)
            return response.body() ?: emptyList()
        }
        //加入某一频道
        suspend fun joinChannel(channelId: Int): String {
            val response = ApiService.instance.joinChannel(channelId)
            return response.body() ?: "加入失败"
        }
        //举报某一频道
        suspend fun reportChannel(channelId: Int, content: String): String {
            val dto = ChannelReportDto(channelId, content)
            val response = ApiService.instance.reportChannel(dto)
            return response.body() ?: "举报失败"
        }
    }

}