package com.example.rickandmortyapp.data.room

import androidx.room.TypeConverter
import com.example.rickandmortyapp.data.models.DataCharacters.*
import com.example.rickandmortyapp.data.models.DataCharacters.CharacterRAM.*
import com.google.gson.Gson

private const val nullStr = "null"
/**
 * @author Axel Sanchez
 */
class Converters{
    private val gson: Gson = Gson()

    @TypeConverter
    fun fromCharacter(character: CharacterRAM?): String? {
        character?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toCharacter(character: String?): CharacterRAM? {
        character?.let {
            return gson.fromJson(it, CharacterRAM::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromLocation(location: Location?): String? {
        location?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toLocation(location: String?): Location? {
        location?.let {
            return gson.fromJson(it, Location::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromOrigin(origin: Origin?): String? {
        origin?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toOrigin(origin: String?): Origin? {
        origin?.let {
            return gson.fromJson(it, Origin::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromEpisodes(episodes: List<String?>?): String? {
        var response = ""
        episodes?.let {
            for (i in episodes.indices) {
                response += if (i == 0) episodes[i]
                else ";${episodes[i]}"
            }
        } ?: return null
        return response
    }

    @TypeConverter
    fun toEpisodes(concat: String?): List<String?>? {
        val list = concat?.split(";")
        list?.let {
            return it.map { str -> if (str != nullStr) str else null }
        } ?: return null
    }
}