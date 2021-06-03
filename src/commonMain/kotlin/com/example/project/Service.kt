package com.example.project

import io.kvision.annotations.KVService
import kotlinx.serialization.Serializable

@Serializable
data class MovieCharacter(val name: String, val title: String)

@KVService
interface ICharacterService {
    suspend fun getCharacters(): List<MovieCharacter>
    suspend fun addCharacter(character: MovieCharacter): Boolean

    suspend fun movies(search: String?, state: String?): List<String>
}
