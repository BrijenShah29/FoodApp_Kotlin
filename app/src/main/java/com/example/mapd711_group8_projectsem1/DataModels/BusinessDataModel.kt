package com.example.mapd711_group8_projectsem1.DataModels

import android.os.Parcel
import android.os.Parcelable

data class BusinessDataModel(
    val name : String ? = null,
    val address : String? = null,
    val image : String? = null,
    val hours: Hours? = null,
    var menus: List<Menus?>? = null
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Hours::class.java.classLoader),
        parcel.createTypedArrayList(Menus)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(image)
        parcel.writeParcelable(hours, flags)
        parcel.writeTypedList(menus)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BusinessDataModel> {
        override fun createFromParcel(parcel: Parcel): BusinessDataModel {
            return BusinessDataModel(parcel)
        }

        override fun newArray(size: Int): Array<BusinessDataModel?> {
            return arrayOfNulls(size)
        }
    }

}

data class Hours(
    val Sunday : String ? = null,
    val Monday : String ? = null,
    val Tuesday : String? = null,
    val Wednesday : String ? = null,
    val Thursday : String? = null,
    val Friday : String? = null,
    val Saturday : String? = null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Sunday)
        parcel.writeString(Monday)
        parcel.writeString(Tuesday)
        parcel.writeString(Wednesday)
        parcel.writeString(Thursday)
        parcel.writeString(Friday)
        parcel.writeString(Saturday)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Hours> {
        override fun createFromParcel(parcel: Parcel): Hours {
            return Hours(parcel)
        }

        override fun newArray(size: Int): Array<Hours?> {
            return arrayOfNulls(size)
        }
    }
}

data class Menus(
    val name: String? = null,
    val price : String? = null,
    val url : String? = null,
    var totalItems : Int? = null
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeString(url)
        parcel.writeValue(totalItems)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Menus> {
        override fun createFromParcel(parcel: Parcel): Menus {
            return Menus(parcel)
        }

        override fun newArray(size: Int): Array<Menus?> {
            return arrayOfNulls(size)
        }
    }

}

