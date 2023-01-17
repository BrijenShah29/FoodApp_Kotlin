package com.example.mapd711_group8_projectsem1

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapd711_group8_projectsem1.DataModels.BusinessDataModel
import com.example.mapd711_group8_projectsem1.RecyclerViewAdapter.BusinessListAdapter
import com.example.mapd711_group8_projectsem1.roomDatabase.AppDatabase
import com.example.mapd711_group8_projectsem1.roomDatabase.DatabaseEntity
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*

class MainActivity : AppCompatActivity(), BusinessListAdapter.BusinessListClickListener {

    private lateinit var appDB: AppDatabase
    private lateinit var userEmail: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.elevation = 0F
        actionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FE7F11")))



        val email = intent.getStringExtra("email_id")
        userEmail = email.toString()

        appDB = AppDatabase.getDatabase(this)
        var databasePlace: DatabaseEntity
        var firstName: String


        GlobalScope.launch {
            databasePlace = appDB.databaseDao().loadAllWithEmail(userEmail)
            firstName = databasePlace.firstName!!
            actionBar?.title = "Welcome $firstName,"
        }

        val businessDataModel = getBusinessData()
        //initializing recycler view
        initRecyclerView(businessDataModel)

    }

    private fun initRecyclerView(businessDataModel: List<BusinessDataModel?>?) {
        val recyclerViewBusiness = findViewById<RecyclerView>(R.id.recyclerViewBusinesses)
        recyclerViewBusiness!!.setHasFixedSize(true)
        recyclerViewBusiness.layoutManager = LinearLayoutManager(this)
        val displayAdapter = BusinessListAdapter(this, businessDataModel, this)
        recyclerViewBusiness.adapter = displayAdapter

    }


    private fun getBusinessData(): List<BusinessDataModel?> {
        val inputStream: InputStream = resources.openRawResource(R.raw.businesses)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1000)
        try {
            val reader: Reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != 0) {
                writer.write(buffer, 0, n)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val jsonString: String = writer.toString()
        val gson = Gson()
        return gson.fromJson(jsonString, Array<BusinessDataModel>::class.java).toList()

    }

    override fun onItemClick(businessDataModel: BusinessDataModel) {
        val intent = Intent(this, ProductDisplayActivity::class.java)
        intent.putExtra("BusinessModel", businessDataModel)
        intent.putExtra("email_id", userEmail)
        startActivity(intent)

    }


}
