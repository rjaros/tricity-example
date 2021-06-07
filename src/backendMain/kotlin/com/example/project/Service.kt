package com.example.project

actual class CharacterService : ICharacterService {

    override suspend fun getCharacters(): List<MovieCharacter> {
        return Repostiory.getCharacters()
    }

    override suspend fun add(movieCharacter: MovieCharacter): Boolean {
        Repostiory.add(movieCharacter)
        return true
    }

    override suspend fun movies(search: String?, state: String?): List<String> {
        return Repostiory.getCharacters().map { it.title }.distinct()
    }

}

object Repostiory {
    private val characters = mutableListOf<MovieCharacter>()

    fun getCharacters() = characters

    fun add(movieCharacter: MovieCharacter) {
        characters += movieCharacter
    }
}
