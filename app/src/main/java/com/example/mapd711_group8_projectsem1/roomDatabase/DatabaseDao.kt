package com.example.mapd711_group8_projectsem1.roomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Query

@Dao
interface DatabaseDao {
    @Query("SELECT * FROM PlacesTable")
    fun fetchAll() : List<DatabaseEntity>

    @Query("SELECT * FROM PlacesTable WHERE email LIKE :email")
    suspend fun loadAllWithEmail(email : String) : DatabaseEntity

    @Insert(onConflict = REPLACE)
    suspend fun insert(insertPlaces : DatabaseEntity)

    @Query("UPDATE PlacesTable SET cardHolderName=:cardHolderName, cardNumber=:cardNumber, cardMonth=:cardMonth, cardYear=:cardYear, cardCVV=:cardCVV WHERE email LIKE :email ")
    suspend fun updateCardInfo(cardHolderName : String, cardNumber:String, cardMonth : String, cardYear : String, cardCVV : String, email : String)

    @Delete
    suspend fun delete(places : DatabaseEntity)

    @Query("DELETE FROM PlacesTable")
    suspend fun deleteAll()


}