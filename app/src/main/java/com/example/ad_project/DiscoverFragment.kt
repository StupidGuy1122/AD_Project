package com.example.ad_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ad_project.databinding.FragmentDiscoverBinding
import kotlinx.coroutines.launch

class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private var isLiked = false

    private val activityId = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 设置模拟数据
        binding.profileName.text = "tennis"
        binding.profileDate.text = "2023/10/15"

        lifecycleScope.launch {
            try {
                val activity = ApiService.getActivityById(activityId)
                if (activity != null) {
                    binding.profileName.text = activity.title
                    binding.profileDate.text = activity.startTime
                } else {
                    binding.profileName.text = "活动不存在"
                }
            } catch (e: Exception) {
                binding.profileName.text = "加载异常"
                e.printStackTrace()
            }
        }

        // 点赞按钮点击事件
        binding.heartIcon.setOnClickListener {
            isLiked = !isLiked
            // 示例：切换图标资源
            // binding.heartIcon.setImageResource(if (isLiked) R.drawable.ic_heart_filled else R.drawable.ic_heart)
        }

        // 点击卡片跳转详情页
        binding.profileCard.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("activityId", activityId)
            }
            findNavController().navigate(R.id.action_discoverFragment_to_activityDetailsFragment, bundle)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
