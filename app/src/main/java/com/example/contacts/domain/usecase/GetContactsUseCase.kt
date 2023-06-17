package com.example.contacts.domain.usecase

import com.example.contacts.common.Result
import com.example.contacts.domain.model.Contact
import com.example.contacts.domain.repository.ContactsRepository
import com.example.contacts.presentation.contacts.adapter.ContactsAdapter
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository
) {

    suspend fun getContacts(): Result<List<ContactsAdapter.ListItem>> {
        val data = contactsRepository.getContacts()
        return when (data) {
            is Result.Success -> {
                val list = sortList(data.data)
                Result.Success(getTransformedList(list))
            }

            is Result.Error -> {
                Result.Error(data.message)
            }

            is Result.Exception -> {
                Result.Exception(data.e)
            }
        }
    }

    suspend fun getFilteredContacts(str: String): Result<List<ContactsAdapter.ListItem>> {
        return when (val data = contactsRepository.getContacts()) {
            is Result.Success -> {
                val list = sortList(data.data)
                Result.Success(filter(str, list))
            }

            is Result.Error -> {
                Result.Error(data.message)
            }

            is Result.Exception -> {
                Result.Exception(data.e)
            }
        }
    }

    private fun filter(str: String, list: List<Contact>): List<ContactsAdapter.ListItem> {
        return if (!str.contains("[0-9]".toRegex())) {
            getTransformedList(list.filter { it.name.contains(str, ignoreCase = true) })
        } else {
            getTransformedList(list.filter {
                it.number.replace("\\s".toRegex(), "").contains(str, ignoreCase = true)
            }
            )
        }
    }

    private fun sortList(data: List<Contact>): List<Contact> {
        val sortedList = data.toMutableList()
        sortedList.forEach {
            it.filterNumber()
        }
        sortedList.sortBy { it.name.first().toUpperCase() }
        return sortedList
    }

    private fun getTransformedList(data: List<Contact>): List<ContactsAdapter.ListItem> {
        val transformedList = mutableListOf<ContactsAdapter.ListItem>()
        var currentHeader: Char? = null
        for (contact in data) {
            val firstChar = contact.name.first().toUpperCase()
            if (firstChar != currentHeader?.toUpperCase()) {
                transformedList.add(ContactsAdapter.ListItem.GroupItem(firstChar.toString()))
                currentHeader = firstChar
            }
            transformedList.add(ContactsAdapter.ListItem.UiContact(contact))
        }
        return transformedList.toList()
    }
}
