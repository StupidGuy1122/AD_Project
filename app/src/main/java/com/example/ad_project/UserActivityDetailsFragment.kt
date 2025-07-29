package com.example.ad_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ad_project.databinding.FragmentActivityDetailsBinding
import kotlinx.coroutines.launch


class UserActivityDetailsFragment : Fragment() {
    private var _binding: FragmentActivityDetailsBinding? = null
    private val binding get() = _binding!!
    private var activityId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            activityId = it.getInt("activityId", -1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivityDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            try {
                val activity = ApiService.getActivityById(activityId)
                if (activity != null) {
                    binding.activityTitle.text = activity.title
                    binding.activityDescription.text = activity.description
                    binding.activityStartTime.text = activity.startTime
                    binding.activityEndTime.text = activity.endTime
                    binding.activityStatus.text = activity.status
                } else {
                    binding.activityTitle.text = "活动不存在"
                }
            } catch (e: Exception) {
                binding.activityTitle.text = "加载异常"
                e.printStackTrace()
            }
        }

        binding.exitActivityDetails.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}