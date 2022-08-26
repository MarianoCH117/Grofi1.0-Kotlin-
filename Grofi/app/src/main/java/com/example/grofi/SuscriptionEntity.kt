package com.example.grofi

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
-Proyecto: Grofi
-Package:  com.example.grofi
-Autor: Mariano Cervantes
-Realizado el 02/04/2022 10:57 a. m.
*/
@Entity(tableName = "SuscriptionEntity")
data class SuscriptionEntity(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                             var name: String,
                             var numPerson: Long = 0,
                             var date:String = "",
                             var costSuscriprion: Long = 0,
                             var costSuscriprionP: Long = 0,
                             var imgUrl: String = ""){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SuscriptionEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
