package com.example.ad_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ad_project.adapter.ChannelActivityAdapter
import com.example.ad_project.adapter.ChannelAdapter
import com.example.ad_project.adapter.MessageAdapter
import com.example.ad_project.databinding.FragmentChannelDetailsBinding
import com.example.ad_project.data.Channel
import kotlinx.coroutines.launch
import java.io.IOException


class ChannelDetailsFragment : Fragment() {
    private var _binding: FragmentChannelDetailsBinding? = null
    private val binding get() = _binding!!
    private var channelId: Int = -1
    private lateinit var channelActivityAdapter: ChannelActivityAdapter
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            channelId = ChannelDetailsFragmentArgs.fromBundle(it).channelId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChannelDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 初始化活动列表适配器
        channelActivityAdapter = ChannelActivityAdapter { activity ->
            // 点击活动跳转到活动详情
            val action = ChannelDetailsFragmentDirections
                .actionChannelDetailsFragmentToActivityDetailsFragment(activity.activityId.toString())
            findNavController().navigate(action)
        }
        binding.activitiesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = channelActivityAdapter
        }

        // 初始化消息列表适配器
        messageAdapter = MessageAdapter()
        binding.messagesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = messageAdapter
        }
        // 加载频道详情
        loadChannelDetails()
        // 加入频道按钮
        binding.joinButton.setOnClickListener {
            joinCurrentChannel()
        }
    }
    private fun loadChannelDetails() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                // 获取频道列表
                val channelList = ApiService.getChannelDetailsList() // 假设返回 List<ChannelDetails>
                if (channelList.isNullOrEmpty()) {
                    Toast.makeText(context, "没有频道数据", Toast.LENGTH_SHORT).show()
                } else {
                    for (channel in channelList) {
                        // 对每个频道执行操作，例如更新UI或添加到列表
                        updateUI(channel)
                    }
                }
            } catch (e: IOException) {
                Toast.makeText(context, "网络错误，请检查连接", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun updateUI(channel: Channel) {
        binding.apply {
            // 基本信息
            channelName.text = channel.name
            channelDescription.text = channel.description
            organizerName.text = "组织者: ${channel.organizerUsername}"
            memberCount.text = "成员数量: ${channel.memberCount}"
            // 标签显示
            tagsText.text = channel.tags.joinToString(" | ") { it.name }
            // 活动列表
            //channelActivityAdapter.submitList(channel.activities)
            // 消息列表
            messageAdapter.submitList(channel.messages)
            // 根据是否已加入更新UI
            if (channel.isJoined) {
                joinButton.text = "已加入"
                joinButton.isEnabled = false
                messageInputLayout.visibility = View.VISIBLE // 显示消息输入框
            } else {
                joinButton.text = "加入频道"
                joinButton.isEnabled = true
                messageInputLayout.visibility = View.GONE // 隐藏消息输入框
            }
        }
    }

    private fun joinCurrentChannel() {
        lifecycleScope.launch {
            try {
                val result = ApiService.joinChannel(channelId)
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                // 重新加载详情刷新状态
                loadChannelDetails()
            } catch (e: Exception) {
                Toast.makeText(context, "加入失败: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}