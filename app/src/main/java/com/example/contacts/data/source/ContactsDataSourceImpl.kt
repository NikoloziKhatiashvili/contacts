package com.example.contacts.data.source

import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import com.example.contacts.domain.model.Contact
import com.example.contacts.domain.repository.source.ContactsDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ContactsDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ContactsDataSource {

    override suspend fun getContacts(): List<Contact> {
        return getNamePhoneDetails()
    }

    private fun getNamePhoneDetails(): MutableList<Contact> {
        val contacts = mutableListOf<Contact>()
        val cr: ContentResolver = context.contentResolver
        val cur = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
            null, null, null
        )
        if (cur != null && cur.count > 0) {
            while (cur.moveToNext()) {
                val id =
                    cur.getString(cur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID))
                val name =
                    cur.getString(cur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number =
                    cur.getString(cur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contacts.add(Contact(id, name, number))
            }
        }
        return contacts
    }
}
