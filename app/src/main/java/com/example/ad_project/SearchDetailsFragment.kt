package com.example.ad_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.ad_project.data.Activity
import com.example.ad_project.databinding.FragmentSearchDetailsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchDetailsFragment : Fragment() {

    private var _binding: FragmentSearchDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchDetailsBinding.inflate(inflater, container, false)

        // 使用 Safe Args 获取 activityId
        val args = SearchDetailsFragmentArgs.fromBundle(requireArguments())
        val activityId = args.activityId

        loadActivityDetails(activityId)

        return binding.root
    }

    private fun loadActivityDetails(activityId: Int) {
        lifecycleScope.launch {
            try {
                val activity = withContext(Dispatchers.IO) {
                    ApiService.getActivityById(activityId)
                }

                if (activity != null) {
                    bindActivityDetails(activity)
                } else {
                    Toast.makeText(requireContext(), "活动未找到", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "加载失败: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun bindActivityDetails(activity: Activity) {
        binding.searchDetailsTitle.text = activity.title
        binding.searchDetailsStartTime.text = activity.startTime
        binding.searchDetailsDescription.text = activity.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
