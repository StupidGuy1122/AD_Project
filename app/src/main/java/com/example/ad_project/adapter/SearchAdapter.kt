package com.example.ad_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ad_project.R
import com.example.ad_project.data.Activity

class SearchAdapter(
    private val activities: List<Activity>,
    private val onItemClick: (Activity) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.activityTitle)
        private val startTime: TextView = itemView.findViewById(R.id.activitystartTime)
        private val description: TextView = itemView.findViewById(R.id.activityDesc)

        fun bind(activity: Activity) {
            title.text = activity.title
            startTime.text = activity.startTime
            description.text = activity.description

            itemView.setOnClickListener {
                onItemClick(activity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(activities[position])
    }

    override fun getItemCount(): Int = activities.size
}
