package com.example.mapd711_group8_projectsem1

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.mapd711_group8_projectsem1.DataModels.BusinessDataModel
import kotlinx.android.synthetic.main.activity_order_success.*

class OrderSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_success)
        val actionBar : ActionBar? = supportActionBar
        val businessModel : BusinessDataModel? = intent.getParcelableExtra("BusinessModel")
        val name = intent.getStringExtra("firstName")
        userGreeting.text = getString(R.string.congratulations,name)
        confirmationText.text = getString(R.string.success,businessModel!!.name!!)
        actionBar?.elevation = 0F
        actionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FE7F11")))
        actionBar.title = businessModel.name
        actionBar.subtitle = businessModel.address

        buttonDone.setOnClickListener {
            finish()
        }
    }

}