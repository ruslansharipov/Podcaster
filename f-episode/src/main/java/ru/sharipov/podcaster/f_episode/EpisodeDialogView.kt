package ru.sharipov.podcaster.f_episode

import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxBottomSheetDialogFragment
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxPresenter
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import javax.inject.Inject

class EpisodeDialogView : BaseRxBottomSheetDialogFragment(), CrossFeatureFragment {

    @Inject
    lateinit var presenter: EpisodePresenter

    @Inject
    lateinit var sh: EpisodeStateHolder

    override fun createConfigurator() = EpisodeScreenConfigurator(arguments)

    override fun getPresenters() = emptyArray<BaseRxPresenter>()

    override fun getScreenName(): String = "EpisodeDialogView"
}