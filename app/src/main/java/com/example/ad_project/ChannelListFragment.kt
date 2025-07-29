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
import com.example.ad_project.adapter.ChannelAdapter
import com.example.ad_project.databinding.FragmentChannelListBinding
import kotlinx.coroutines.launch
import java.io.IOException

class ChannelListFragment : Fragment() {
    private var _binding: FragmentChannelListBinding? = null
    private val binding get() = _binding!!
    private lateinit var channelAdapter: ChannelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChannelListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 初始化适配器
        channelAdapter = ChannelAdapter { channel ->
            // 点击频道项跳转详情页
            val action = ChannelListFragmentDirections
                .actionChannelListFragmentToChannelDetailsFragment(channel.channelId)
            findNavController().navigate(action)
        }

        // 配置RecyclerView
        binding.channelsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = channelAdapter
        }

        // 加载频道列表
        loadChannels()

        // 下拉刷新
        binding.swipeRefresh.setOnRefreshListener {
            loadChannels()
        }
    }

    private fun loadChannels() {
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                // 调用ApiService获取频道列表（假设新增了getAllChannels接口）
                val channels = ApiService.getAllChannels()
                if (channels.isNotEmpty()) {
                    channelAdapter.submitList(channels)
                    binding.emptyState.visibility = View.GONE
                } else {
                    binding.emptyState.visibility = View.VISIBLE
                }
            } catch (e: IOException) {
                Toast.makeText(context, "网络错误，请检查连接", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}