package com.example.newsapp.data.db.userdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.newsapp.data.model.user.User

@Database(entities = [User::class], version = 2)
abstract class UserDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object{
        const val NAME = "MY_ROOM_DATABASE"

        val MIGRATION_2_3 = object: Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE 'User' ('id' INTEGER PRIMARY KEY, " +
                        "'name' TEXT NOT NULL, 'email' TEXT NOT NULL)"
                )
            }

        }
    }
}