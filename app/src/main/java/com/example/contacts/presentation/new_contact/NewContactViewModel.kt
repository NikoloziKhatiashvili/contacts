package com.example.contacts.presentation.new_contact

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.common.Result
import com.example.contacts.domain.usecase.AddContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewContactViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase
) : ViewModel() {

    private val _contactsState = MutableStateFlow<AddContactUi>(AddContactUi.Empty)
    val contactsState = _contactsState.asStateFlow()

    fun addContact(name: String, lastName: String, number: String, email: String, image: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            _contactsState.emit(AddContactUi.Loading())
            when (val res = addContactUseCase.invoke(name, lastName, number, email, image)) {
                is Result.Success -> _contactsState.emit(AddContactUi.SuccessUi(res.data))
                is Result.Error -> _contactsState.emit(AddContactUi.ErrorUi(res.message))
                is Result.Exception -> _contactsState.emit(AddContactUi.ErrorUi(res.e.message))
            }
        }
    }
}
