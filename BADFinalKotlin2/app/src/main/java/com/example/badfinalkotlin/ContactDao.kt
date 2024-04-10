package com.example.badfinalkotlin

import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact_table ORDER BY name ASC")
    fun getAlphabetizedContacts(): Flow<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    @Query("DELETE FROM contact_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(contact: Contact)

    @Update
    suspend fun update(contact: Contact)
}