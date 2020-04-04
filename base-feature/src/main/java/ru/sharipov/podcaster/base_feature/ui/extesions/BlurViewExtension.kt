package ru.sharipov.podcaster.base_feature.ui.extesions

import android.view.ViewGroup
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.BlurViewFacade
import eightbitlab.com.blurview.RenderScriptBlur

/**
 * Настроить работу `BlurView` с `target` с параметрами по-умолчанию:
 * * Алгоритм: RenderScriptBlur;
 * * Радиус: 16px;
 * * Автообновление: вкл.
 * */
fun BlurView.setupWithDefault(target: ViewGroup): BlurViewFacade {
    return setupWith(target)
        .setBlurAlgorithm(RenderScriptBlur(context))
        .setBlurRadius(16f)
        .setBlurAutoUpdate(true)
        .setBlurEnabled(true)
}