package ru.rendezvous.app.ui.activity.di

import android.os.Bundle
import ru.sharipov.podcaster.base_feature.ui.screen.FragmentScreenModule
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent
import ru.surfstudio.android.core.mvp.configurator.BaseFragmentViewConfigurator
import ru.surfstudio.android.core.ui.activity.CoreActivityInterface
import ru.surfstudio.android.mvp.dialog.complex.CoreBottomSheetDialogFragmentView

/**
 * Базовый конфигуратор для экрана основанного на [CoreBottomSheetDialogFragmentView]
 */
abstract class BottomDialogScreenConfigurator(
        args: Bundle
) : BaseFragmentViewConfigurator<ActivityComponent, FragmentScreenModule>(args) {

    override fun getFragmentScreenModule(): FragmentScreenModule {
        return FragmentScreenModule(persistentScope)
    }

    override fun getParentComponent(): ActivityComponent { //TODO добавить диалог компонент?
        return (getTargetFragmentView<CoreBottomSheetDialogFragmentView>().activity as CoreActivityInterface)
                .persistentScope
                .configurator
                .activityComponent as ActivityComponent
    }
}