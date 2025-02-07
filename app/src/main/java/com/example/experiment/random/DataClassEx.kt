package com.example.experiment.random

data class StreakFlameTypesText(
    val subtitle: Subtitle?,
    val title: Title?
)

data class Subtitle(
    val baseText: String?,
    val flipText: String?
)

data class Title(
    val baseText: String?,
    val flipText: String?
)