package com.example.contacts.di

import com.example.contacts.domain.repository.AddContactRepository
import com.example.contacts.domain.repository.ContactsRepository
import com.example.contacts.domain.usecase.AddContactUseCase
import com.example.contacts.domain.usecase.GetContactsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ContactModule {

    @Provides
    fun provideGetContactsUseCase(repository: ContactsRepository): GetContactsUseCase {
        return GetContactsUseCase(repository)
    }

    @Provides
    fun provideAddContactUseCase(repository: AddContactRepository): AddContactUseCase {
        return AddContactUseCase(repository)
    }
}
