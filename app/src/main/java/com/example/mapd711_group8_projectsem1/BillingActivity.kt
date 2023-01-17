package com.example.mapd711_group8_projectsem1

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mapd711_group8_projectsem1.DataModels.BusinessDataModel
import com.example.mapd711_group8_projectsem1.RecyclerViewAdapter.BillingListAdapter
import com.example.mapd711_group8_projectsem1.databinding.ActivityBillingBinding
import com.example.mapd711_group8_projectsem1.roomDatabase.AppDatabase
import com.example.mapd711_group8_projectsem1.roomDatabase.DatabaseEntity
import kotlinx.android.synthetic.main.activity_billing.*
import kotlinx.android.synthetic.main.activity_billing.cardCVV
import kotlinx.android.synthetic.main.activity_billing.cardCVVLayout
import kotlinx.android.synthetic.main.activity_billing.cardHolderName
import kotlinx.android.synthetic.main.activity_billing.cardMonth
import kotlinx.android.synthetic.main.activity_billing.cardMonthLayout
import kotlinx.android.synthetic.main.activity_billing.cardNameLayout
import kotlinx.android.synthetic.main.activity_billing.cardNumber
import kotlinx.android.synthetic.main.activity_billing.cardNumberLayout
import kotlinx.android.synthetic.main.activity_billing.cardYear
import kotlinx.android.synthetic.main.activity_billing.cardYearLayout
import kotlinx.android.synthetic.main.activity_billing.fNameLayout
import kotlinx.android.synthetic.main.activity_billing.lNameLayout
import kotlinx.android.synthetic.main.activity_billing.pNumberLayout
import kotlinx.android.synthetic.main.activity_billing.paymentBanner
import kotlinx.android.synthetic.main.activity_billing.provinceLayout
import kotlinx.android.synthetic.main.activity_billing.slashLayput
import kotlinx.android.synthetic.main.activity_billing.street1Layout
import kotlinx.android.synthetic.main.activity_billing.street2Layout
import kotlinx.android.synthetic.main.activity_billing.zipLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Suppress("UNNECESSARY_NOT_NULL_ASSERTION", "UNNECESSARY_SAFE_CALL")
class BillingActivity : AppCompatActivity() {


    private lateinit var appDB : AppDatabase
    private lateinit var userEmail : String
    private lateinit var firstName : String
    private lateinit var lastName : String
    private lateinit var street1 : String
    private lateinit var street2 :String
    private lateinit var province : String
    private lateinit var zipcode : String
    private lateinit var phoneNumber: String
    private var cardHoldersName : String ? = null
    private var cardDigit : String? = null
    private var cardMonthMM : String? = null
    private var cardYearYY : String? = null
    private var cardCVVCode : String? = null
    private lateinit var binding: ActivityBillingBinding
    private lateinit var databaseUser : DatabaseEntity

    private var billingListAdapter : BillingListAdapter ? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBillingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val businessModel : BusinessDataModel? = intent.getParcelableExtra("businessModel")
        val email = intent.getStringExtra("email_id")
        userEmail = email.toString()
        appDB = AppDatabase.getDatabase(this)


        val actionBar : ActionBar? = supportActionBar
        actionBar?.elevation = 0F
        actionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FE7F11")))
        actionBar.subtitle = businessModel!!.name

        initRecyclerView(businessModel)

        GlobalScope.launch {
            databaseUser = appDB.databaseDao().loadAllWithEmail(userEmail)
            firstName = databaseUser.firstName!!
            lastName = databaseUser.lastName!!
            street1 = databaseUser.streetOne!!
            street2 = databaseUser.streetTwo!!
            province = databaseUser.province!!
            zipcode = databaseUser.zipcode!!
            phoneNumber = databaseUser.phoneNumber!!
            cardHoldersName = databaseUser.cardHolderName!!
            cardDigit = databaseUser.cardNumber!!
            cardMonthMM = databaseUser.cardMonth!!
            cardYearYY = databaseUser.cardYear!!
            cardCVVCode = databaseUser.cardCVV!!
            actionBar?.title = "$firstName's Checkout"

        }

        totalAmountCalculation(businessModel)


        continuePayment.setOnClickListener{

            visibilityIsTrue()
            firstNameInput.setText(firstName)
            lastNameInput.setText(lastName)
            street1Input.setText(street1)
            street2Input.setText(street2)
            provinceInput.setText(province)
            zipcodeInput.setText(zipcode)
            phoneNumberInput.setText(phoneNumber)

                if(cardDigit!!.length >= 13)
                {

                    cardHolderName.setText(cardHoldersName)
                    cardNumber.setText(cardDigit)
                    cardMonth.setText(cardMonthMM)
                    cardYear.setText(cardYearYY)
                    cardCVV.setText(cardCVVCode)
                    switchPrefPayment.isChecked = true

                }
            continuePayment.visibility = View.GONE
            parentScroll.post {
                parentScroll.fullScroll(View.FOCUS_DOWN)
            }

        }


        binding.switchPrefPayment?.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                bindingTextFields()
                updateDatabase()
            }

        }
        binding.switchCOD?.setOnCheckedChangeListener { _, isChecked ->

            if(isChecked){
                visibilityIsFalse()
            }
            else{
                visibilityIsTrue()
            }
        }

        placeOrder.setOnClickListener {

            bindingTextFields()

            if(switchCOD.isChecked)
            {
                val intent = Intent(this, OrderSuccessActivity::class.java)
                intent.putExtra("BusinessModel",businessModel)
                intent.putExtra("firstName",firstName)
                startActivityForResult(intent,500)

            }else{

            if(cardHoldersName!!.isEmpty() || cardHoldersName!!.length<=2)
            {
                cardHolderName.error = "Enter Card Holder Name"
                return@setOnClickListener
            }
            else if(cardDigit!!.isEmpty() || cardDigit!!.length<=2)
            {
                cardNumber.error = "Enter card Number "
                return@setOnClickListener
            }else if (cardMonthMM!!.isEmpty() || cardMonthMM!!.length<2){
                cardMonth.error = "Enter Card Expiry Month (MM)"
                return@setOnClickListener
            }else if (cardYearYY!!.isEmpty() || cardYearYY!!.length<2){
                cardYear.error = "Enter Card Expiry Year (MM)"
                return@setOnClickListener
            }else if (cardCVVCode!!.isEmpty() || cardCVVCode!!.length<3){
                cardCVV.error = "Enter CVV Code (CVV)"
                return@setOnClickListener
            }

            val intent = Intent(this, OrderSuccessActivity::class.java)
                intent.putExtra("firstName",firstName)
            intent.putExtra("BusinessModel",businessModel)

            startActivityForResult(intent,500)
            }

        }

    }

    private fun updateDatabase() {

        GlobalScope.launch(Dispatchers.IO)
        {
            appDB.databaseDao().updateCardInfo(cardHoldersName!!,cardDigit!!,cardMonthMM!!,cardYearYY!!,cardCVVCode!!,userEmail!!)
        }
    }


    private fun totalAmountCalculation(businessModel: BusinessDataModel?) {

        var subTotal = 0f
        var deliverCharge = 0f
        var tax = 0F
        var finalTotal = 0f
        for(menu in businessModel?.menus!!){
            deliverCharge = (if(menu?.totalItems!! <= 5 ) menu?.totalItems!! * 1.5F else 5.0F)

            subTotal += menu?.price!!.toFloat() * menu?.totalItems!!.toFloat()
            tax = (subTotal * 0.14).toFloat()
            finalTotal = subTotal + tax
        }
        tvDeliveryChargeAmount.text = String.format("$ %.2f", deliverCharge)

        tvSubtotalAmount.text = String.format("$ %.2f",subTotal)

        tvTaxAmount.text = String.format("$ %.2f",tax)

        tvTotalAmount.text = String.format("$ %.2f",finalTotal)


    }

    private fun initRecyclerView(businessModel : BusinessDataModel?) {
        cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        billingListAdapter = BillingListAdapter(this, businessModel?.menus)
        cartItemsRecyclerView.adapter = billingListAdapter

    }

    private fun bindingTextFields() {

        cardHoldersName = binding.cardHolderName.text.toString()
        cardDigit = binding.cardNumber.text.toString()
        cardMonthMM = binding.cardMonth.text.toString()
        cardYearYY = binding.cardYear.text.toString()
        cardCVVCode = binding.cardCVV.text.toString()

    }

    private fun visibilityIsTrue() {

        cardNameLayout.visibility = View.VISIBLE
        cardNumberLayout.visibility = View.VISIBLE
        cardMonthLayout.visibility = View.VISIBLE
        slashLayput.visibility = View.VISIBLE
        cardYearLayout.visibility = View.VISIBLE
        cardCVVLayout.visibility = View.VISIBLE
        deliveryBanner.visibility = View.VISIBLE
        paymentBanner.visibility = View.VISIBLE
        switchPrefPayment.visibility = View.VISIBLE
        switchCOD.visibility = View.VISIBLE
        fNameLayout.visibility = View.VISIBLE
        lNameLayout.visibility = View.VISIBLE
        street1Layout.visibility = View.VISIBLE
        street2Layout.visibility = View.VISIBLE
        provinceLayout.visibility = View.VISIBLE
        zipLayout.visibility = View.VISIBLE
        pNumberLayout.visibility = View.VISIBLE
        placeOrder.visibility = View.VISIBLE

    }

    private fun visibilityIsFalse() {
        cardNameLayout.visibility = View.GONE
        cardNumberLayout.visibility = View.GONE
        cardMonthLayout.visibility = View.GONE
        slashLayput.visibility = View.GONE
        cardYearLayout.visibility = View.GONE
        cardCVVLayout.visibility = View.GONE
        switchPrefPayment.visibility = View.GONE
        paymentBanner.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 500){
            setResult(RESULT_OK)
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }
}