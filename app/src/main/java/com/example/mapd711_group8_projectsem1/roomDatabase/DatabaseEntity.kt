package com.example.mapd711_group8_projectsem1.roomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PlacesTable")
data class DatabaseEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    @ColumnInfo(name = "email")
    var email: String? = null,

    @ColumnInfo(name = "firstName")
    var firstName: String? = null,

    @ColumnInfo(name = "lastName")
    var lastName: String? = null,

    @ColumnInfo(name = "streetOne")
    var streetOne: String? = null,

    @ColumnInfo(name = "streetTne")
    var streetTwo: String? = null,

    @ColumnInfo(name = "province")
    var province: String? = null,

    @ColumnInfo(name = "zipcode")
    var zipcode: String? = null,

    @ColumnInfo(name = "phoneNumber")
    var phoneNumber: String ? = null,

    @ColumnInfo(name = "cardHolderName")
    var cardHolderName: String ? = null,

    @ColumnInfo(name = "cardNumber")
    var cardNumber: String ? = null,

    @ColumnInfo(name = "cardMonth")
    var cardMonth: String ? = null,

    @ColumnInfo(name = "cardYear")
    var cardYear: String ? = null,

    @ColumnInfo(name = "cardCVV")
    var cardCVV: String ? = null,

)
