package ru.rendezvous.app.ui.activity.di

import android.os.Bundle
import ru.surfstudio.android.core.mvp.configurator.BaseFragmentViewConfigurator
import ru.surfstudio.android.core.ui.activity.CoreActivityInterface
import ru.surfstudio.android.mvp.dialog.complex.CoreDialogFragmentView
import ru.sharipov.podcaster.base_feature.ui.screen.FragmentScreenModule
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent

/**
 * Базовый конфигуратор для экрана основанного на [CoreDialogFragmentView]
 */
abstract class DialogScreenConfigurator(
        args: Bundle
) : BaseFragmentViewConfigurator<ActivityComponent, FragmentScreenModule>(args) {

    override fun getFragmentScreenModule(): FragmentScreenModule {
        return FragmentScreenModule(persistentScope)
    }

    override fun getParentComponent(): ActivityComponent { //TODO добавить диалог компонент?
        return (getTargetFragmentView<CoreDialogFragmentView>().activity as CoreActivityInterface)
                .persistentScope
                .configurator
                .activityComponent as ActivityComponent
    }
}