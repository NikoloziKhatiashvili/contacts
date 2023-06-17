package com.example.contacts.domain.repository

import android.net.Uri
import com.example.contacts.common.Result

interface AddContactRepository {

    suspend fun addContact(
        name: String,
        lastName: String,
        number: String,
        email: String,
        image: Uri?
    ): Result<String>
}
