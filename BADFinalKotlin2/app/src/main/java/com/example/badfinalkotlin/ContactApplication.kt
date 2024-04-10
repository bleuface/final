package com.example.badfinalkotlin

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ContactApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { ContactRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ContactRepository(database.contactDao()) }
}