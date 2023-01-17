package com.example.mapd711_group8_projectsem1.RecyclerViewAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mapd711_group8_projectsem1.DataModels.Menus
import com.example.mapd711_group8_projectsem1.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_item_list.view.*

@Suppress("UNNECESSARY_SAFE_CALL")
class ItemListAdapter(private val context: Context, private val itemList: List<Menus?>?, val clickListener:ItemListClickListener ):
    RecyclerView.Adapter<ItemListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view :View = LayoutInflater.from(context).inflate(R.layout.recycler_item_list, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(itemList?.get(position)!!)

    }


    override fun getItemCount(): Int {
        return itemList?.size ?: return 0
    }

    inner class MyViewHolder(view : View):RecyclerView.ViewHolder(view) {

        var thumbImage: ImageView = view.thumbImage
        val menuName: TextView = view.menuName
        val menuPrice: TextView = view.menuPrice
        val addToCartButton: TextView = view.addToCartButton
        val addMoreLayout: LinearLayout = view.addMoreLayout
        val imageMinus: ImageView = view.imageMinus
        val imageAddOne: ImageView = view.imageAddOne
        val tvCount: TextView = view.tvCount

        @SuppressLint("SetTextI18n")
        fun bind(menus: Menus){
            menuName.text = menus?.name
            menuPrice.text = "Price: $ ${menus?.price}"
            addToCartButton.setOnClickListener{
                menus?.totalItems = 1
                clickListener.addToCartClickListener(menus)
                addMoreLayout?.visibility = View.VISIBLE
                addToCartButton.visibility = View.GONE
                tvCount.text = menus?.totalItems.toString()
            }

            imageMinus.setOnClickListener{
                var total: Int = menus.totalItems!!
                total--
                if(total > 0) {
                    menus?.totalItems = total
                    clickListener.updateCartClickListener(menus)
                    tvCount.text = menus?.totalItems.toString()
                }
                else {
                    menus.totalItems = total
                    clickListener.removeFromCartClickListener(menus)
                    addMoreLayout.visibility = View.GONE
                    addToCartButton.visibility = View.VISIBLE
                }

            }
            imageAddOne.setOnClickListener{
                var total: Int = menus.totalItems!!
                total++
                if(total <= 10) {
                    menus.totalItems = total
                    clickListener.updateCartClickListener(menus)
                    tvCount.text = total.toString()
                }

            }
            Picasso.get().load(menus?.url).fit().centerInside().into(thumbImage)

        }

    }

    interface  ItemListClickListener {
        fun addToCartClickListener(menu: Menus)
        fun updateCartClickListener(menu: Menus)
        fun removeFromCartClickListener(menu: Menus)
    }



}