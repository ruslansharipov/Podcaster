package ru.sharipov.podcaster.f_region

import ru.sharipov.podcaster.domain.Region

data class SelectableRegion(
    val region: Region,
    val isSelected: Boolean
)