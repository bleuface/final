package com.example.badfinalkotlin

import kotlinx.coroutines.CoroutineScope
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.launch

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactRoomDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao
    companion object {
        @Volatile
        private var INSTANCE: ContactRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ContactRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactRoomDatabase::class.java,
                    "word_database"
                ).addCallback(ContactDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class ContactDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.contactDao())
                }
            }
        }

        suspend fun populateDatabase(wordDao: ContactDao) {
            wordDao.insert(Contact("Demo", "xxxxxxxxx"))
        }
    }
}
