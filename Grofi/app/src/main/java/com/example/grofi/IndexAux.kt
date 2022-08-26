package com.example.grofi

/*
-Proyecto: Grofi
-Package:  com.example.grofi
-Autor: Mariano Cervantes
-Realizado el 02/04/2022 12:19 p. m.
*/
interface IndexAux {
    fun hideFab(isVisible: Boolean = false)

    fun addsuscription(suscriptionEntity: SuscriptionEntity)
    fun updatesuscription(suscriptionEntity: SuscriptionEntity)
}