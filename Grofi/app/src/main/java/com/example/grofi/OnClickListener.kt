package com.example.grofi

/*
-Proyecto: Grofi
-Package:  com.example.grofi
-Autor: Mariano Cervantes
-Realizado el 02/04/2022 11:54 a. m.
*/
interface OnClickListener {
    fun onClick(suscriptonID: Long)
    fun onFavoriteSuscription(suscriptionEntity: SuscriptionEntity)
    fun onDeleteSuscription(suscriptionEntity: SuscriptionEntity)
}