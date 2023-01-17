package com.example.mapd711_group8_projectsem1.UserAuthenticationFirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.mapd711_group8_projectsem1.databinding.ActivityRegistrationBinding
import com.example.mapd711_group8_projectsem1.roomDatabase.AppDatabase
import com.example.mapd711_group8_projectsem1.roomDatabase.DatabaseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Suppress("CanBeVal")
class RegistrationActivity : AppCompatActivity() {


    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var appDB : AppDatabase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        appDB = AppDatabase.getDatabase(this)

        // Getting Email id for fetching Database :

        binding.SignUpButton.setOnClickListener {

            val userEmail = intent.getStringExtra("email_id")
            var email = userEmail.toString()
            var firstName: String? = binding.firstName.text.toString()
            var lastName: String? = binding.lastName.text.toString()
            var street1: String? = binding.street1.text.toString()
            var street2: String? = binding.street2.text.toString()
            var province: String? = binding.province.text.toString()
            var zipcode: String? = binding.zipcode.text.toString()
            var phoneNumber: String? = binding.phoneNumber.text.toString()
            var cardHolderName: String = binding.cardHolderName.text.toString()
            var cardNumber: String = binding.cardNumber.text.toString()
            var cardMonth: String = binding.cardMonth.text.toString()
            var cardYear: String = binding.cardYear.text.toString()
            var cardCVV: String = binding.cardCVV.text.toString()
                   // && cardNumber.length >13 && cardMonth.length ==2 && cardYear.length ==2 && cardCVV.length>=3
            if (firstName!!.isNotEmpty() && lastName!!.isNotEmpty() && street1!!.isNotEmpty() && province!!.isNotEmpty() && zipcode!!.isNotEmpty() && phoneNumber!!.isNotEmpty() ) {

                val insertUser = DatabaseEntity(null,
                    email,
                    firstName,
                    lastName,
                    street1,
                    street2,
                    province,
                    zipcode,
                    phoneNumber,
                    cardHolderName,
                    cardNumber,
                    cardMonth,
                    cardYear,
                    cardCVV)

                GlobalScope.launch(Dispatchers.IO)
                {
                    appDB.databaseDao().insert(insertUser)

                }

                Toast.makeText(this, "New User Added ! Please Login Again !", Toast.LENGTH_SHORT)
                    .show()

                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            } else{
                Toast.makeText(this, "Please Fill the required Information !!", Toast.LENGTH_SHORT)
                    .show()
            }


        }
    }
}