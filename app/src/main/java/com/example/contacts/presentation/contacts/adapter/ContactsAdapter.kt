package com.example.contacts.presentation.contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.databinding.ContactItemBinding
import com.example.contacts.databinding.GroupItemBinding
import com.example.contacts.domain.model.Contact


class ContactsAdapter : ListAdapter<ContactsAdapter.ListItem, RecyclerView.ViewHolder>(
    ContactsItemDiffUtilCallBack()
) {

    companion object {
        const val GROUP_VIEW_TYPE = 0
        const val CONTACTS_VIEW_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            GROUP_VIEW_TYPE -> {
                val view =
                    GroupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GroupViewHolder(view)
            }

            CONTACTS_VIEW_TYPE -> {
                val view =
                    ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ContactsViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder.itemViewType) {
            GROUP_VIEW_TYPE -> (holder as GroupViewHolder).bind(item as ListItem.GroupItem)
            CONTACTS_VIEW_TYPE -> (holder as ContactsViewHolder).bind(item as ListItem.UiContact)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.GroupItem -> GROUP_VIEW_TYPE
            is ListItem.UiContact -> CONTACTS_VIEW_TYPE
            else -> throw IllegalArgumentException("Invalid data type")
        }
    }

    sealed class ListItem(val id: Int) {
        data class GroupItem(val nameHeader: String) : ListItem(nameHeader.hashCode())
        data class UiContact(
            val contact: Contact,
        ) : ListItem(contact.id.toInt())
    }
}

