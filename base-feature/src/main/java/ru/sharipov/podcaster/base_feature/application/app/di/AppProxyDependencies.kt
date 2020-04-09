package ru.sharipov.podcaster.base_feature.application.app.di

import android.content.Context
import android.content.SharedPreferences
import ru.sharipov.podcaster.base.provider.StringsProvider
import ru.sharipov.podcaster.base_feature.ui.bus.InsetsInteractor
import ru.sharipov.podcaster.i_genres.RegionsInteractor
import ru.sharipov.podcaster.i_listen.PodcastInteractor
import ru.surfstudio.android.activity.holder.ActiveActivityHolder
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.core.ui.navigation.activity.navigator.GlobalNavigator
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.rx.extension.scheduler.SchedulersProvider
import ru.surfstudio.android.shared.pref.NO_BACKUP_SHARED_PREF
import javax.inject.Named

/**
 * Интерфейс, объединяющий в себе все зависимости в скоупе [PerApplication]
 * Следует использовать в компоненте Application и других компонентах более высоких уровней,
 * зависящих от него.
 */
interface AppProxyDependencies {
    fun context(): Context
    fun activeActivityHolder(): ActiveActivityHolder
    fun connectionProvider(): ConnectionProvider
    fun schedulerProvider(): SchedulersProvider
    fun stringsProvider(): StringsProvider
    fun globalNavigator(): GlobalNavigator

    @Named(NO_BACKUP_SHARED_PREF)
    fun sharedPreferences(): SharedPreferences

    fun insetsInteractor(): InsetsInteractor
    fun podcastInteractor(): PodcastInteractor
    fun regionInteractor(): RegionsInteractor
}
