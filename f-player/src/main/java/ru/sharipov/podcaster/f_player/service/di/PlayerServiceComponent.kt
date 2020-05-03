package ru.sharipov.podcaster.f_player.service.di

import dagger.Component
import ru.sharipov.podcaster.base_feature.application.app.di.AppComponent
import ru.sharipov.podcaster.f_player.service.PlayerService

@PerService
@Component(dependencies = [AppComponent::class], modules = [PlayerModule::class])
interface PlayerServiceComponent {
    fun inject(service: PlayerService)
}

