package com.example.contacts.data

import android.net.Uri
import com.example.contacts.common.Result
import com.example.contacts.domain.repository.AddContactRepository
import com.example.contacts.domain.repository.source.AddContactDataSource
import javax.inject.Inject

class AddContactRepositoryImpl @Inject constructor(
    private val addContactDataSource: AddContactDataSource
) : AddContactRepository {

    override suspend fun addContact(
        name: String,
        lastName: String,
        number: String,
        email: String,
        image: Uri?
    ): Result<String> {
        return try {
            val data = addContactDataSource.addContact(name, lastName, number, email, image)
            if (data) Result.Success("successfully added contact")
            else Result.Error("something went wrong")
        } catch (e: Exception) {
            Result.Exception(e)
        }
    }
}
