package com.example.grofi

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/*
-Proyecto: Grofi
-Package:  com.example.grofi
-Autor: Mariano Cervantes
-Realizado el 02/04/2022 11:50 a. m.
*/

class SuscriptionApplication:Application(){
    companion object{
        lateinit var database: SuscriptionDatabase
    }

    override fun onCreate() {
        super.onCreate()

        val MIGRATION_1_2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE SuscriptionEntity ADD COLUMN imgUrl TEXT NOT NULL DEFAULT ''")
            }
        }

        database = Room.databaseBuilder(this,
            SuscriptionDatabase::class.java,
            "SuscriptionDatabase")
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}