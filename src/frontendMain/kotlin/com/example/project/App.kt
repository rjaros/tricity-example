package com.example.project

import io.kvision.Application
import io.kvision.core.KVScope
import io.kvision.core.onClickLaunch
import io.kvision.form.formPanel
import io.kvision.form.text.text
import io.kvision.form.text.typeaheadRemote
import io.kvision.html.button
import io.kvision.html.li
import io.kvision.html.ul
import io.kvision.module
import io.kvision.panel.fieldsetPanel
import io.kvision.panel.root
import io.kvision.panel.tab
import io.kvision.panel.tabPanel
import io.kvision.panel.vPanel
import io.kvision.startApplication
import io.kvision.state.bindEach
import io.kvision.state.observableListOf
import io.kvision.state.sub
import io.kvision.utils.perc
import io.kvision.utils.px
import io.kvision.utils.syncWithList
import kotlinx.coroutines.launch

class App : Application() {

    val characterService = CharacterService()

    val characters = observableListOf<MovieCharacter>()

    val startrek = characters.sub { it.filter { it.title == "Star Trek" } }
    val starwars = characters.sub { it.filter { it.title == "Star Wars" } }
    val other = characters.sub { it.filter { it.title != "Star Trek" && it.title != "Star Wars" } }

    override fun start(state: Map<String, Any>) {
        root("kvapp") {
            vPanel {
                padding = 10.px
                width = 100.perc
                tabPanel {
                    tab("Star Trek", "fas fa-hand-spock") {
                        ul().bindEach(startrek) {
                            li(it.name)
                        }
                    }
                    tab("Star Wars", "fas fa-jedi") {
                        ul().bindEach(starwars) {
                            li(it.name)
                        }
                    }
                    tab("Other", "fas fa-spider") {
                        ul().bindEach(other) {
                            li("${it.name} (${it.title})")
                        }
                    }
                }
                formPanel<MovieCharacter> {
                    fieldsetPanel("Movie character") {
                        text(label = "Name:").bind(MovieCharacter::name, required = true)
                        typeaheadRemote(CharacterServiceManager, ICharacterService::movies, label = "Title:").bind(
                            MovieCharacter::title,
                            required = true
                        )
                    }
                    button("Add character", "fas fa-plus").onClickLaunch {
                        with(this@formPanel) {
                            if (this.validate()) {
                                val character = this.getData()
                                characters += character
                                characterService.addCharacter(character)
                                this.clearData()
                                this.focus()
                            }
                        }
                    }
                }
            }
        }
        KVScope.launch {
            val chars = characterService.getCharacters()
            characters.syncWithList(chars)
        }
    }
}

fun main() {
    startApplication(::App, module.hot)
}
