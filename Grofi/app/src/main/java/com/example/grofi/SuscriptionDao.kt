package com.example.grofi

import androidx.room.*

/*
-Proyecto: Grofi
-Package:  com.example.grofi
-Autor: Mariano Cervantes
-Realizado el 02/04/2022 11:48 a. m.
*/
@Dao
interface SuscriptionDao {
    @Query("SELECT * FROM SuscriptionEntity")
    fun getAllSuscription(): MutableList<SuscriptionEntity>

    @Query("SELECT * FROM SuscriptionEntity where id = :id")
    fun getSuscriptionId(id: Long): SuscriptionEntity

    @Insert
    fun addSuscription(suscriptionEntity: SuscriptionEntity): Long

    @Update
    fun updateSuscription(suscriptionEntity: SuscriptionEntity)

    @Delete
    fun deleteSuscription(suscriptionEntity: SuscriptionEntity)

}