package main.kotlin

import java.util.*

data class BirthdayData(
    val birthdayDate: Calendar,
    val personName: String,
    var birthdayPartyDate: String? = null


) {
    override fun toString(): String {
        return "${birthdayDate.time}.} $personName"
    }
}