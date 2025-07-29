package com.example.ad_project

import android.os.Bundle
import android.util.Log
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad_project.adapter.SearchAdapter
import com.example.ad_project.data.Activity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchResultFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private var activities: List<Activity> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_result, container, false)
        recyclerView = view.findViewById(R.id.recyclerView_search_results)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 获取传递的搜索关键词
        val keywordList = arguments?.getStringArray("keyword")
        val keyword = keywordList?.firstOrNull() ?: ""

        // 加载搜索结果
        loadSearchResults(keyword)

        return view
    }

    private fun loadSearchResults(keyword: String) {
        lifecycleScope.launch {
            val activities = ApiService.searchActivities(keyword)  // 网络请求在 IO 线程
            adapter = SearchAdapter(activities) { activity ->
                val action = SearchResultFragmentDirections
                    .actionSearchResultFragmentToSearchDetailsFragment(activity.activityId)
                findNavController().navigate(action)
            }
            recyclerView.adapter = adapter
        }
    }

}