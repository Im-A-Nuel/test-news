package com.coding.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class News(
    val title: String,
    val publishedAt: String,
    val urlToImage: String? = null,
    val description: String,
    val url: String? = null,
    var isFavorite: Boolean = false
): Parcelable