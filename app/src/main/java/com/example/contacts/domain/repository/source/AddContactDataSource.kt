package com.example.contacts.domain.repository.source

import android.net.Uri
import com.example.contacts.common.Result

interface AddContactDataSource {
    suspend fun addContact(
        name: String,
        lastName: String,
        number: String,
        email: String,
        image: Uri?
    ): Boolean
}
