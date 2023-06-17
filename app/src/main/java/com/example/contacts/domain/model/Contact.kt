package com.example.contacts.domain.model

data class Contact(
    val id: String,
    val name: String,
    var number: String
) {

    fun filterNumber(): String {
        return this.number.replace(Regex("[^0-9\\s]"), "").also { this.number = it }
    }
}
