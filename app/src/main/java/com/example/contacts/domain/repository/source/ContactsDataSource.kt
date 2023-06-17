package com.example.contacts.domain.repository.source

import com.example.contacts.domain.model.Contact

interface ContactsDataSource {

    suspend fun getContacts(): List<Contact>
}
