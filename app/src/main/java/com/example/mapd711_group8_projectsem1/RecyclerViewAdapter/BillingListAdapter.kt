package com.example.mapd711_group8_projectsem1.RecyclerViewAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mapd711_group8_projectsem1.DataModels.Menus
import com.example.mapd711_group8_projectsem1.R
import com.squareup.picasso.Picasso

@Suppress("UNNECESSARY_SAFE_CALL")
class BillingListAdapter(private val context: Context, private val menusList : List<Menus?>?): RecyclerView.Adapter<BillingListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view :View = LayoutInflater.from(context).inflate(R.layout.recycler_billing_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menusList?.size ?: return 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bind(menusList?.get(position)!!)
    }


    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val thumbnail : ImageView = view.findViewById(R.id.thumbImage)
        val itemName : TextView = view.findViewById(R.id.itemName)
        val itemPrice : TextView = view.findViewById(R.id.itemPrice)
        val itemQty : TextView = view.findViewById(R.id.itemQty)


        @SuppressLint("SetTextI18n")
        fun bind(menu : Menus)
        {
            itemName.text = menu?.name
            itemPrice.text = "Price $" + String.format("%.2f", menu?.totalItems!!.toFloat() * menu?.price!!.toFloat())
            itemQty.text = " Items: "+ menu?.totalItems.toString()!!
            Picasso.get().load(menu?.url).fit().centerInside().into(thumbnail)


        }

    }

}