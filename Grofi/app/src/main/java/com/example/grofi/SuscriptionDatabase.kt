package com.example.grofi

import androidx.room.Database
import androidx.room.RoomDatabase

/*
-Proyecto: Grofi
-Package:  com.example.grofi
-Autor: Mariano Cervantes
-Realizado el 02/04/2022 11:46 a. m.
*/
@Database(entities = arrayOf(SuscriptionEntity::class), version = 2)
abstract class SuscriptionDatabase : RoomDatabase() {
    abstract fun suscriptionDao(): SuscriptionDao
}