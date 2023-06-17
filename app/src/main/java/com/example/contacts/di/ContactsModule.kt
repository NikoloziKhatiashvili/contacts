package com.example.contacts.di

import com.example.contacts.data.AddContactRepositoryImpl
import com.example.contacts.data.ContactsRepositoryImpl
import com.example.contacts.data.source.AddContactDataSourceImpl
import com.example.contacts.data.source.ContactsDataSourceImpl
import com.example.contacts.domain.repository.AddContactRepository
import com.example.contacts.domain.repository.ContactsRepository
import com.example.contacts.domain.repository.source.AddContactDataSource
import com.example.contacts.domain.repository.source.ContactsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ContactsModule {

    @Binds
    fun bindsContactsRepository(contactsRepositoryImpl: ContactsRepositoryImpl): ContactsRepository

    @Binds
    fun bindsAddContactRepository(addContactRepositoryImpl: AddContactRepositoryImpl): AddContactRepository

    @Binds
    fun bindsContactDataSource(contactsDataSourceImpl: ContactsDataSourceImpl): ContactsDataSource

    @Binds
    fun bindsAddContactDataSource(addContactDataSourceImpl: AddContactDataSourceImpl): AddContactDataSource

}
