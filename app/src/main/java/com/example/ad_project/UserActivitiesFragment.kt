package com.example.ad_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ad_project.adapter.UserActivityAdapter
import com.example.ad_project.databinding.FragmentUserActivitiesBinding
import kotlinx.coroutines.launch

class UserActivitiesFragment : Fragment() {
    private var _binding: FragmentUserActivitiesBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserActivitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedViewModel.userId.observe(viewLifecycleOwner) { id ->
            userId = id
        }
        userId = 1
        loadFavoriteActivities()
        binding.favoriteActivity.setOnClickListener {
            loadFavoriteActivities()
        }
        binding.passedActivity.setOnClickListener {
            loadPassedActivities()
        }
    }

    private fun loadPassedActivities() {
        lifecycleScope.launch {
            try {
                val passedList = ApiService.getPassedActivities()
                val adapter =
                    UserActivityAdapter(requireContext(), R.layout.item_activity, passedList)
                binding.activitiesList.adapter = adapter

                binding.activitiesList.setOnItemClickListener { _, _, position, _ ->
                    val activity = passedList[position]
                    val bundle = Bundle().apply {
                        putInt("activityId", activity.activityId)
                    }
                    findNavController().navigate(R.id.action_userActivitiesFragment_to_activityDetails, bundle)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadFavoriteActivities() {
        lifecycleScope.launch {
            try {
                val favoriteList = ApiService.getFavoriteActivities()
                val adapter =
                    UserActivityAdapter(requireContext(), R.layout.item_activity, favoriteList)
                binding.activitiesList.adapter = adapter

                binding.activitiesList.setOnItemClickListener { _, _, position, _ ->
                    val activity = favoriteList[position]
                    val bundle = Bundle().apply {
                        putInt("activityId", activity.activityId)
                    }
                    findNavController().navigate(R.id.action_userActivitiesFragment_to_activityDetails, bundle)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}