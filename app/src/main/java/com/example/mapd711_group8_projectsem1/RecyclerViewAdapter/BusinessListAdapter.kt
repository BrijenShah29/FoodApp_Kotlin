package com.example.mapd711_group8_projectsem1.RecyclerViewAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mapd711_group8_projectsem1.DataModels.BusinessDataModel
import com.example.mapd711_group8_projectsem1.DataModels.Hours
import com.example.mapd711_group8_projectsem1.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class BusinessListAdapter(private val context : Context, private val businessList : List<BusinessDataModel?>?,val clickListener: BusinessListClickListener):
    RecyclerView.Adapter<BusinessListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {

        val view :View = LayoutInflater.from(context).inflate(R.layout.recycler_business_list, parent, false)

        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(businessList?.get(position))
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(businessList?.get(position)!!)
        }

    }

    override fun getItemCount(): Int {
        return businessList?.size!!
    }

    private fun getTodaysHours(hours: Hours): String?{
        val calendar: Calendar = Calendar.getInstance()
        val date: Date = calendar.time
        val day: String = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.time)
        return when(day) {
            "Sunday" -> hours.Sunday
            "Monday" -> hours.Monday
            "Tuesday" -> hours.Tuesday
            "Wednesday" -> hours.Wednesday
            "Thursday" -> hours.Thursday
            "Friday" -> hours.Friday
            "Saturday" -> hours.Saturday
            else -> hours.Sunday
        }
    }
    interface BusinessListClickListener {
        fun onItemClick(businessDataModel:BusinessDataModel)
    }

    inner class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        var thumbImage: ImageView = view.findViewById(R.id.thumbImage)
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtAddress: TextView = view.findViewById(R.id.txtAddress)
        val txtHours: TextView = view.findViewById(R.id.txtHours)

        @SuppressLint("SetTextI18n")
        fun bind(businessDataModel: BusinessDataModel?) {
            txtName.text = businessDataModel?.name
            txtAddress.text = "Address "+businessDataModel?.address
            txtHours.text = "Today's Hours: " + getTodaysHours(businessDataModel?.hours!!)

            Picasso.get().load(businessDataModel?.image).fit().centerInside().into(thumbImage)


        }

    }

}