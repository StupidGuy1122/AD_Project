package com.example.ad_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController

class SearchFragment : Fragment() {

    private lateinit var editTextKeyword: EditText
    private lateinit var buttonSearch: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        editTextKeyword = view.findViewById(R.id.editText_keyword)
        buttonSearch = view.findViewById(R.id.button_search)

        buttonSearch.setOnClickListener {
            val keyword = editTextKeyword.text.toString()
            if (keyword.isNotBlank()) {
                // 跳转到搜索结果页，并传递关键词
                val action = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(
                    arrayOf(keyword)  // 将关键词作为参数传递
                )
                findNavController().navigate(action)
            }
        }

        return view
    }
}