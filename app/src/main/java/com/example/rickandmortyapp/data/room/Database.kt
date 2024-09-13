package com.example.rickandmortyapp.data.room

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Database
import com.example.rickandmortyapp.data.models.DataCharacters.*
import com.example.rickandmortyapp.data.models.DataCharacters.CharacterRAM.*

/**
 * @author Axel Sanchez
 */
@Database(
    entities = [CharacterRAM::class, Location::class, Origin::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}