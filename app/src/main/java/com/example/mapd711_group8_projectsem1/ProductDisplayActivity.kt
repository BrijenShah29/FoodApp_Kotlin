package com.example.mapd711_group8_projectsem1

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mapd711_group8_projectsem1.DataModels.BusinessDataModel
import com.example.mapd711_group8_projectsem1.DataModels.Menus
import com.example.mapd711_group8_projectsem1.RecyclerViewAdapter.ItemListAdapter
import kotlinx.android.synthetic.main.activity_product_display.*

class ProductDisplayActivity : AppCompatActivity(), ItemListAdapter.ItemListClickListener {

    private var itemList : List<Menus?>? = null
    private  var menuListAdapter: ItemListAdapter? = null
    private var itemsInTheCartList: MutableList<Menus?>? = null
    private var totalItemsInCart : Int = 0
    private lateinit var userEmail : String

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_display)

        val businessModel = intent.getParcelableExtra<BusinessDataModel>("BusinessModel")
        val email = intent.getStringExtra("email_id")
        userEmail = email.toString()

        val actionBar : ActionBar? = supportActionBar
        actionBar?.title = businessModel?.name
        actionBar?.subtitle = businessModel?.address
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FE7F11")))

        itemList = businessModel?.menus
        initRecyclerView(itemList)
        checkoutBtn.setOnClickListener{
            if(itemsInTheCartList !=null && itemsInTheCartList?.size!! <= 0){
                Toast.makeText(this, "Please Add Item in the cart for Checkout", Toast.LENGTH_SHORT).show()
            }else
            {
                businessModel?.menus = itemsInTheCartList
                val intent = Intent(this,BillingActivity::class.java)
                intent.putExtra("businessModel",businessModel)
                intent.putExtra("email_id", userEmail)
                startActivityForResult(intent,500)

            }
        }
    }


    private fun initRecyclerView(items: List<Menus?>?) {
        recyclerViewShopMenu.layoutManager = GridLayoutManager(this, 2)
        menuListAdapter = ItemListAdapter(this,itemList,this)
        recyclerViewShopMenu.adapter = menuListAdapter

    }

    override fun addToCartClickListener(menu: Menus) {
        if(itemsInTheCartList == null){
            itemsInTheCartList = ArrayList()
        }
        itemsInTheCartList?.add(menu)
        totalItemsInCart = 0
        for(menu in itemsInTheCartList!!){
            totalItemsInCart += menu?.totalItems!!

        }
        checkoutBtn.text = String.format("Checkout $totalItemsInCart Items")



    }

    override fun updateCartClickListener(menu: Menus) {
            val index = itemsInTheCartList!!.indexOf(menu)
            itemsInTheCartList?.removeAt(index)
            itemsInTheCartList?.add(menu)
            totalItemsInCart = 0
            for(menu in itemsInTheCartList!!) {
                totalItemsInCart += menu?.totalItems!!
            }
        checkoutBtn.text = String.format("Checkout $totalItemsInCart Items")

        }

    override fun removeFromCartClickListener(menu: Menus) {
        val index = itemsInTheCartList!!.indexOf(menu)
        itemsInTheCartList?.removeAt(index)
        itemsInTheCartList?.remove(menu)
        totalItemsInCart = 0
        for(menu in itemsInTheCartList!!) {
            totalItemsInCart += menu?.totalItems!!
        }
        checkoutBtn.text = String.format("Checkout $totalItemsInCart Items")


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
        }

    // Start Activity for Result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 500 && resultCode == RESULT_OK)
            finish()
    }



}