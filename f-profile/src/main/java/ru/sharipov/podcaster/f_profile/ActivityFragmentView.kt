package ru.sharipov.podcaster.f_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.sharipov.podcaster.base_feature.ui.controller.EpisodeController
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
import ru.sharipov.podcaster.base_feature.ui.placeholder.PlaceholderState
import ru.sharipov.podcaster.base_feature.ui.placeholder.PlaceholderStateView
import ru.sharipov.podcaster.domain.Episode
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView
import ru.surfstudio.android.easyadapter.EasyAdapter
import javax.inject.Inject

class ActivityFragmentView : BaseRxFragmentView() {

    @Inject
    lateinit var presenter: ActivityPresenter

    @Inject
    lateinit var sh: ActivityStateHolder

    private val easyAdapter = EasyAdapter()
    private val episodeController = EpisodeController(
        isFullEpisode = true,
        clickListener = { presenter.onEpisodeClick(it) }
    )

    override fun createConfigurator() = ActivityConfigurator()

    override fun getScreenName(): String = "ActivityFragmentView"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        activity_settings_btn.setOnClickListener { presenter.onSettingsClick() }

        activity_rv.layoutManager = LinearLayoutManager(requireContext())
        activity_rv.adapter = easyAdapter

        sh.bindTo(::render)
    }

    private fun render(state: ActivityState) {
        activity_psv.performIfChanged(state.placeholderState, PlaceholderStateView::setState)
        activity_rv.performIfChanged(state.historyItems) { items: List<Episode> ->
            easyAdapter.setData(items, episodeController)
        }
    }
}