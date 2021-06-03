package com.example.project

actual class CharacterService : ICharacterService {

    override suspend fun getCharacters(): List<MovieCharacter> {
        return Repository.getCharacters()
    }

    override suspend fun addCharacter(character: MovieCharacter): Boolean {
        Repository.addCharacter(character)
        return true
    }

    override suspend fun movies(search: String?, state: String?): List<String> {
        return Repository.getCharacters().map { it.title }.distinct()
    }
}

object Repository {

    private val characters = mutableListOf<MovieCharacter>()

    fun getCharacters() = characters

    fun addCharacter(movieCharacter: MovieCharacter) {
        characters += movieCharacter
    }

}
