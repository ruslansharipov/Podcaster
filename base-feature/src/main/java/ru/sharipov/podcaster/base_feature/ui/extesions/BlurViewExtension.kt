package ru.sharipov.podcaster.base_feature.ui.extesions

import android.view.ViewGroup
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.BlurViewFacade
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.base_feature.ui.blur.StackBlurAlgorithm

/**
 * Настроить работу `BlurView` с `target` с параметрами по-умолчанию:
 * * Алгоритм: RenderScriptBlur;
 * * Радиус: 16px;
 * * Автообновление: вкл.
 * */
fun BlurView.setupWithDefault(target: ViewGroup): BlurViewFacade {
    return setupWith(target)
        .setBlurAlgorithm(StackBlurAlgorithm())
        .setBlurRadius(60f)
        .setBlurAutoUpdate(true)
        .setBlurEnabled(true)
        .setHasFixedTransformationMatrix(true)
        .setOverlayColor(context.color(R.color.white_50))
}