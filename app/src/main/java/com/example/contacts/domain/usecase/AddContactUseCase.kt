package com.example.contacts.domain.usecase

import android.net.Uri
import com.example.contacts.common.Result
import com.example.contacts.domain.repository.AddContactRepository
import com.example.contacts.presentation.common.isValidEmail
import javax.inject.Inject

class AddContactUseCase @Inject constructor(
    private val addContactRepository: AddContactRepository
) {
    suspend operator fun invoke(
        name: String,
        lastName: String,
        number: String,
        email: String,
        image: Uri?
    ): Result<String> {
        if (name.isEmpty() || number.isEmpty() || email.isEmpty()) {
            return Result.Error("Fill all the fields")
        }
        if (!email.isValidEmail()) {
            return Result.Error("invalid email")
        }
        return addContactRepository.addContact(name, lastName, number, email, image)
    }
}
