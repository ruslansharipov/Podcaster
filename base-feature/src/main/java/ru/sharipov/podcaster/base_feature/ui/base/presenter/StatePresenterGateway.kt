package ru.sharipov.podcaster.base_feature.ui.base.presenter

import android.os.Bundle
import ru.surfstudio.android.core.ui.event.lifecycle.completely.destroy.OnCompletelyDestroyDelegate
import ru.surfstudio.android.core.ui.event.lifecycle.pause.OnPauseDelegate
import ru.surfstudio.android.core.ui.event.lifecycle.ready.OnViewReadyDelegate
import ru.surfstudio.android.core.ui.event.lifecycle.resume.OnResumeDelegate
import ru.surfstudio.android.core.ui.event.lifecycle.start.OnStartDelegate
import ru.surfstudio.android.core.ui.event.lifecycle.stop.OnStopDelegate
import ru.surfstudio.android.core.ui.event.lifecycle.view.destroy.OnViewDestroyDelegate
import ru.surfstudio.android.core.ui.state.ScreenState

/**
 * Сущность связывающая презентер с методами жизненного цикла вью.
 * В отличие от CorePresenter не хранит ссылку на вью и не может вызывать методы вью напрямую.
 */
class StatePresenterGateway(
    private val presenter: StatePresenter,
    private val screenState: ScreenState
) : OnViewReadyDelegate,
    OnStartDelegate,
    OnResumeDelegate,
    OnPauseDelegate,
    OnStopDelegate,
    OnViewDestroyDelegate,
    OnCompletelyDestroyDelegate {

    override fun onViewReady() {
        val isRecreated = screenState.isViewRecreated
        if (!isRecreated) {
            presenter.onFirstLoad()
        }
        presenter.onLoad(isRecreated)
    }

    override fun onStart() {
        presenter.onStart()
    }

    override fun onResume() {
        presenter.onResume()
    }

    override fun onPause() {
        presenter.onPause()
    }

    override fun onStop() {
        presenter.onStop()
    }

    override fun onViewDestroy() {
        presenter.onViewDestroy()
    }

    override fun onCompletelyDestroy() {
        presenter.onCompletelyDestroy()
    }
}