package ru.sharipov.podcaster.f_explore

import ru.surfstudio.android.core.mvp.presenter.BasePresenter
import ru.surfstudio.android.core.mvp.presenter.BasePresenterDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class ExplorePresenter @Inject constructor(
    basePresenterDependency: BasePresenterDependency
) : BasePresenter<ExploreFragmentView>(basePresenterDependency) {

}
