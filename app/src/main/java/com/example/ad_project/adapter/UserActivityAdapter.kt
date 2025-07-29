package com.example.ad_project.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.ad_project.R
import com.example.ad_project.data.Activity

class UserActivityAdapter(
    context: Context,
    private val resource: Int,
    private val activities: List<Activity>
) : ArrayAdapter<Activity>(context, resource, activities) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val activity = activities[position]
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val titleText = view.findViewById<TextView>(R.id.activityTitle)
        val descText = view.findViewById<TextView>(R.id.activityDesc)
        val locationText = view.findViewById<TextView>(R.id.activityLocation)
        val startTimeText = view.findViewById<TextView>(R.id.activitystartTime)
        val endTimeText = view.findViewById<TextView>(R.id.activityendTime)

        titleText.text = activity.title
        descText.text = activity.description
        locationText.text = activity.location
        startTimeText.text = activity.startTime
        endTimeText.text = activity.endTime

        view.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("activityId", activity.activityId)
            }
            view.findNavController().navigate(R.id.action_userActivitiesFragment_to_activityDetails, bundle)
        }

        return view
    }
}