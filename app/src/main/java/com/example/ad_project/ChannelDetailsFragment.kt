package com.example.ad_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.ad_project.databinding.FragmentChannelDetailsBinding
import com.example.ad_project.data.Channel
import kotlinx.coroutines.launch
import java.io.IOException


class ChannelDetailsFragment : Fragment() {
    private var _binding: FragmentChannelDetailsBinding? = null
    private val binding get() = _binding!!
    private var channelId: Int = -1

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
                val channelList = null //ApiService.getChannelDetailsList()
                if (true) {
                    Toast.makeText(context, "没有频道数据", Toast.LENGTH_SHORT).show()
                } else {
                    val channel = null //channelList.find { it.channelId == channelId }
                    if (channel != null) {
                        updateUI(channel)
                    } else {
                        Toast.makeText(context, "未找到指定频道", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: IOException) {
                Toast.makeText(context, "网络错误，请检查连接", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "加载失败: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }


    private fun updateUI(channel: Channel) {
        val isJoined = true
        binding.apply {
            channelName.text = channel.name
            channelDescription.text = channel.description
            organizerName.text = "组织者: ${channel.creator?.name}"
            memberCount.text = "成员数量: ${channel.members.size}"
            tagsText.text = channel.tags.joinToString(" | ") { it.name }

            if (isJoined) {
                joinButton.text = "leave"
                joinButton.isEnabled = false
            } else {
                joinButton.text = "join"
                joinButton.isEnabled = true
            }
        }
    }

    private fun joinCurrentChannel() {
        lifecycleScope.launch {
            try {
                val result = ApiService.joinChannel(channelId)
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
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
