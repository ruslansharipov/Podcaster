package ru.sharipov.podcaster.f_player_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_player_expanded.*
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxDialogView
import javax.inject.Inject

class PlayerDialogView : BaseRxDialogView() {

    @Inject
    lateinit var presenter: PlayerDialogPresenter

    @Inject
    lateinit var sh: PlayerStateHolder

    override fun getTheme(): Int = R.style.AppTheme_Light_ModalAnimationDialog

    override fun createConfigurator() = PlayerScreenConfigurator()

    override fun getScreenName(): String = "PlayerDialogView"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.layout_player_expanded, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        initView()
        sh.bindTo(::renderState)
    }

    private fun initView() {
        player_close_ib.setOnClickListener { dismiss() }
    }

    private fun renderState(state: PlayerState) {

    }
}