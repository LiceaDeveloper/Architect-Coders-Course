package com.liceadev.architectcoders.model.database

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun stringToUser(stringUser: String?): User? = Gson().fromJson(stringUser, User::class.java)

    @TypeConverter
    fun userToString(user: User?): String? = Gson().toJson(user)
}