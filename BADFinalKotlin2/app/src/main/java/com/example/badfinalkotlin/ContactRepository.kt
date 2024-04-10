package com.example.badfinalkotlin

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ContactRepository(private val contactDao: ContactDao) {

    val allContacts: Flow<List<Contact>> = contactDao.getAlphabetizedContacts()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }
    @WorkerThread
    suspend fun deleteAll() {
        contactDao.deleteAll()
    }
    @WorkerThread
    suspend fun delete(contact: Contact) {
        contactDao.delete(contact)
    }
    @WorkerThread
    suspend fun update(contact: Contact) {
        contactDao.update(contact)
    }
}