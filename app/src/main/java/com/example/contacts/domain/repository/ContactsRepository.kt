package com.example.contacts.domain.repository

import com.example.contacts.common.Result
import com.example.contacts.domain.model.Contact

interface ContactsRepository {

    suspend fun getContacts(): Result<List<Contact>>

}
