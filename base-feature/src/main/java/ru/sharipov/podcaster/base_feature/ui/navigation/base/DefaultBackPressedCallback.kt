package ru.sharipov.podcaster.base_feature.ui.navigation.base

import androidx.activity.OnBackPressedCallback

class DefaultBackPressedCallback(
    private val onBackPressed: () -> Unit
) : OnBackPressedCallback(true) {

    override fun handleOnBackPressed() {
        onBackPressed()
    }
}