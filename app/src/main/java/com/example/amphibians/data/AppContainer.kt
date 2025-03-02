package com.example.amphibians.data

interface AppContainer {
    val amphibianRepository: AmphibianRepository
}
class DefaultAppContainer: AppContainer {



    override val amphibianRepository: AmphibianRepository


}