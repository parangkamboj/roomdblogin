package com.example.kitlabs.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo(name = "fName")
    var firstName : String? = null,
    @ColumnInfo(name = "lName")
    var lastName: String? = null,
    @ColumnInfo(name = "email")
    var email: String? = null,
    @ColumnInfo(name = "password")
    var password: String? = null,
)