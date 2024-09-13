package com.example.rickandmortyapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmortyapp.data.models.DataCharacters.*

/**
 * @author Axel Sanchez
 */
@Dao
interface CharacterDao {
    @Query("SELECT * FROM CharacterRAM WHERE PAGE = :page")
    suspend fun getAllCharacters(page: Int): List<CharacterRAM?>

    @Query("SELECT * FROM CharacterRAM where id = :idCharacter")
    suspend fun getCharacter(idCharacter: Int): CharacterRAM?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterRAM): Long
}